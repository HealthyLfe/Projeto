package com.example.healthylife6.usuario.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.healthylife6.R
import com.example.healthylife6.classe.idRecSelecionado
import com.example.healthylife6.model.ReceitaModel
import com.example.healthylife6.model.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detalhes_cafe.*
import kotlinx.android.synthetic.main.fragment_editar_perfil_user.*
import kotlinx.android.synthetic.main.receitas_home_cafe.*

/**
 * A simple [Fragment] subclass.
 */
class DetalhesCafeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detalhes_cafe, container, false)
        infoReceita()
        return view
    }

    private fun infoReceita() {

        var ref = FirebaseDatabase.getInstance().getReference("cafe da manha").child(idRecSelecionado)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rec = dataSnapshot.getValue(ReceitaModel::class.java)

                tit_cafe.text = rec?.titulo
                ingred_cafe.text = rec?.ingrediente
                desc_cafe.text = rec?.desc
                Picasso.get().load(rec?.img).into(cafe_det)
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

}
