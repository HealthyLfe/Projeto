package com.example.healthylife6.adm.fragment


import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import com.example.healthylife6.R
import com.example.healthylife6.classe.Usuario
import com.example.healthylife6.classe.emailLogado
import com.example.healthylife6.model.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_adm_editar_perfil.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AdmEditarPerfilFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adm_editar_perfil, container, false)

        val btnCancelar = view.findViewById<View>(R.id.can_adm_perfil) as Button
        btnCancelar.setOnClickListener {
            val fragment = AdmPerfilFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnSalvar = view.findViewById<View>(R.id.edit_adm_perfil) as Button
        btnSalvar.setOnClickListener {
            editar()
            val fragment = AdmPerfilFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        infoUser()

        return view
    }

    lateinit var idadeAntiga: String
    lateinit var pesoAntigo: String
    lateinit var altAntiga: String
    lateinit var idAntigo: String
    lateinit var nome: String
    lateinit var email: String
    lateinit var senha: String
    lateinit var sexo: String

    private fun infoUser() {
        auth = FirebaseAuth.getInstance()
        var ref = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                 var user = dataSnapshot.getValue(UsuarioModel::class.java)

                idAntigo = user!!.userId!!

                edit_idade_adm.setText(user?.idade)
                edit_idade_adm.setSelection(edit_idade_adm.text.length)

                edit_peso_adm.setText(user?.peso)
                edit_peso_adm.setSelection(edit_peso_adm.text.length)

                edit_alt_adm.setText(user?.altura)
                edit_alt_adm.setSelection(edit_alt_adm.text.length)

                idadeAntiga = user?.idade.toString()
                pesoAntigo = user?.peso.toString()
                altAntiga = user?.altura.toString()
                email = user?.email.toString()
                idAntigo = user?.userId.toString()
                nome = user?.nome.toString()
                senha = user?.senha.toString()
                sexo = user?.sexo.toString()

                //var mmc =  (pesoAntigo * pesoAntigo)/altAntiga

            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

    private fun editar() {
        edit_idade_adm.setSelection(edit_idade_adm.text.length)
        edit_peso_adm.setSelection(edit_peso_adm.text.length)
        edit_alt_adm.setSelection(edit_alt_adm.text.length)

        val idade = edit_idade_adm.text.toString().trim()
        val peso = edit_peso_adm.text.toString().trim()
        val alt = edit_alt_adm.text.toString().trim()

        if (idade.isEmpty() || peso.isEmpty() || alt.isEmpty()) {
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        } else {
                val ref = FirebaseDatabase.getInstance().getReference("Users").child(idAntigo)
                Log.d("AdmditarFragment", "id user atual: $idAntigo")
                val user = UsuarioModel(idAntigo, nome, sexo, idade, peso, alt, email, senha)
                ref.setValue(user)
            }
        Toast.makeText(activity, "Alterações salvas com sucesso!", Toast.LENGTH_LONG).show()
    }
}


