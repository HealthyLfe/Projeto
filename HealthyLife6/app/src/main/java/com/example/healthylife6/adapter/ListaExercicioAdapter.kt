package com.example.healthylife6.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.healthylife6.R
import com.example.healthylife6.adm.activity.AdmHomeActivity
import com.example.healthylife6.adm.fragment.EditarAlmocoFragment
import com.example.healthylife6.adm.fragment.EditarExercicioFragment
import com.example.healthylife6.classe.categoriaAdm
import com.example.healthylife6.classe.idExercicioAdm
import com.example.healthylife6.classe.idReceitaAdm
import com.example.healthylife6.model.ExercicioModel
import com.example.healthylife6.model.ReceitaModel
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ListaExercicioAdapter(private val context: Context, private val listExercicio: ArrayList<ExercicioModel>) : RecyclerView.Adapter<ListaExercicioAdapter.MyViewHolder>() {

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (exerId, titulo, desc, img) = listExercicio[i]

        Picasso.get().load(listExercicio[i].img).into(myViewHolder.img)
        myViewHolder.titulo.text = titulo

        myViewHolder.btnExcluir.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("exercicios")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmação de exclusão")
            builder.setMessage("Deseja realmente excluir: $titulo?")
            Log.d("ExclusaoExerActivity", "ID RECEITA: $exerId")

            builder.setPositiveButton("Sim") { dialog, which ->
                Toast.makeText(context, "Exercício excluído!", Toast.LENGTH_SHORT).show()
                ref.child(exerId).removeValue()
            }

            builder.setNegativeButton("Não") { dialog, which ->
                Toast.makeText(context, "Exclusão cancelada!", Toast.LENGTH_SHORT).show()
            }

            builder.show()
        }

        myViewHolder.btnEditar.setOnClickListener {
            idExercicioAdm = exerId

            val fragment = EditarExercicioFragment()
            val transaction = (context as AdmHomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerAdm, fragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return listExercicio.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.exercicio_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var titulo: TextView
        var btnEditar: Button
        var btnExcluir: Button

        init {
            img = itemView.findViewById(R.id.img_row_exer)
            titulo = itemView.findViewById(R.id.titulo_row_exer)
            btnEditar = itemView.findViewById(R.id.btnEditar_row_exer)
            btnExcluir = itemView.findViewById(R.id.btnExluir_row_exer)
        }
    }
}