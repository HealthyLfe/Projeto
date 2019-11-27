package com.example.healthylife6.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthylife6.R
import com.example.healthylife6.classe.idExerSelecionado
import com.example.healthylife6.model.ExercicioModel
import com.example.healthylife6.usuario.activity.HomeActivity
import com.example.healthylife6.usuario.fragment.DetalhesExercicioFragment
import com.squareup.picasso.Picasso

class ExercicioAdapter(private val context: Context, private val listExercicio: ArrayList<ExercicioModel>): RecyclerView.Adapter<ExercicioAdapter.MyViewHolder>(){

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var(exerId, titulo, desc, img) = listExercicio[i]

        Picasso.get().load(listExercicio[i].img).into(myViewHolder.img)
        myViewHolder.titulo.text = titulo

        myViewHolder.itemView.setOnClickListener {
            idExerSelecionado = exerId

            val fragment = DetalhesExercicioFragment()
            val transaction = (context as HomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment).addToBackStack(null).commit()
        }
    }


    override fun getItemCount(): Int {
        return listExercicio.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.exercicio_home, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var titulo: TextView
        var btnVoltar: Button

        init {
            img = itemView.findViewById(R.id.img_exer_home)
            titulo = itemView.findViewById(R.id.titulo_exer_home)
            btnVoltar = itemView.findViewById(R.id.voltar_exer)
        }
    }
}