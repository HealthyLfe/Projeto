package com.example.healthylife6.adm.fragment


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.healthylife6.LoginActivity

import com.example.healthylife6.R
import com.example.healthylife6.classe.emailLogado
import com.example.healthylife6.model.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_adm_perfil.*

/**
 * A simple [Fragment] subclass.
 */
class AdmPerfilFragment : Fragment() {

    lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adm_perfil, container, false)

        ref = FirebaseDatabase.getInstance().getReference("Users")

        val btnEditarPerfilAdm = view.findViewById<View>(R.id.btnEditarPerfilAdm) as ImageView

        btnEditarPerfilAdm.setOnClickListener {
            val fragment = AdmEditarPerfilFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnExcluir = view.findViewById<View>(R.id.btnExcluir) as CardView

        btnExcluir.setOnClickListener {
            excluir()
        }

        val btnSair = view.findViewById<View>(R.id.btnSair) as CardView

        btnSair.setOnClickListener {
            sair()
        }


        infoUser()

        return view
    }
    private fun infoUser() {
        auth = FirebaseAuth.getInstance()
        var ref = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(UsuarioModel::class.java)
                nome_adm.text = user?.nome
                email_adm.text = user?.email
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

    private fun sair() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Sair")
        builder.setMessage("Deseja mesmo sair do aplicativo?")

        builder.setPositiveButton("Sim") { dialog, which ->
            auth = FirebaseAuth.getInstance()
            var idUserAtual = auth.currentUser?.uid
            Log.d("AdmAjustesFragment", "id user atual: $idUserAtual")
            auth.signOut()
            val i = Intent(activity, LoginActivity::class.java)
            startActivity(i)
        }

        builder.setNegativeButton("Não") { dialog, which ->
            Toast.makeText(
                activity,
                "Ação cancelada!",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.show()
    }

    private fun excluir() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Excluir conta")
        builder.setMessage("Deseja mesmo excluir a sua conta?")

        builder.setPositiveButton("Sim") { dialog, which ->
            Toast.makeText(
                activity,
                "Sua conta foi excluída!",
                Toast.LENGTH_SHORT
            ).show()
            auth = FirebaseAuth.getInstance()
            var user = auth.currentUser
            ref.child(emailLogado).removeValue()
            user?.delete()
            val i = Intent(activity, LoginActivity::class.java)
            startActivity(i)
        }

        builder.setNegativeButton("Não") { dialog, which ->
            Toast.makeText(
                activity,
                "Exclusão cancelada!",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.show()
    }
}
