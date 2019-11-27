package com.example.healthylife6.usuario.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.healthylife6.R
import com.example.healthylife6.classe.idExerSelecionado
import com.example.healthylife6.model.ExercicioModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detalhes_exercicio.*

/**
 * A simple [Fragment] subclass.
 */
class DetalhesExercicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detalhes_exercicio, container, false)
        infoExercicio()
        return view    }

    private fun infoExercicio() {

        var ref = FirebaseDatabase.getInstance().getReference("exercicios").child(idExerSelecionado)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val exer = dataSnapshot.getValue(ExercicioModel::class.java)

                exer_tit.text = exer?.titulo
                exer_tempo.text = exer?.tempo
                desc_exer.text = exer?.desc
                Picasso.get().load(exer?.img).into(exer_det)
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

}



