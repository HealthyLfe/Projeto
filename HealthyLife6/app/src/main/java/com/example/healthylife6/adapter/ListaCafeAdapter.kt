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
import com.example.healthylife6.adm.fragment.EditarCafeFragment
import com.example.healthylife6.classe.categoriaAdm
import com.example.healthylife6.classe.idReceitaAdm
import com.example.healthylife6.model.ReceitaModel
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

data class ListaCafeAdapter (
    private val context: Context,
    private val listCafe: ArrayList<ReceitaModel>) : RecyclerView.Adapter<ListaCafeAdapter.MyViewHolder>() {

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (recId, titulo, ingr, desc, categoria, img) = listCafe[i]

        Picasso.get().load(listCafe[i].img).into(myViewHolder.img)
        myViewHolder.titulo.text = titulo

        myViewHolder.btnExcluir.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("cafe da manha")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmação de exclusão")
            builder.setMessage("Deseja realmente excluir: $titulo?")
            Log.d("ExclusaoReceitaActivity", "ID RECEITA CAFE: $recId")

            builder.setPositiveButton("Sim") { dialog, which ->
                Toast.makeText(context, "Receita excluída!", Toast.LENGTH_SHORT).show()
                ref.child(recId).removeValue()
                Log.d("ExclusaoReceitaActivity","AAAAAAAAA")
            }

            builder.setNegativeButton("Não") { dialog, which ->
                Toast.makeText(context, "Exclusão cancelada!", Toast.LENGTH_SHORT).show()
            }

            builder.show()
        }

        myViewHolder.btnEditar.setOnClickListener {
            idReceitaAdm = recId
            categoriaAdm = categoria

            val fragment = EditarCafeFragment()
            val transaction = (context as AdmHomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerAdm, fragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return listCafe.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.receita_row_cafe, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var titulo: TextView
        var btnEditar: Button
        var btnExcluir: Button

        init {
            img = itemView.findViewById(R.id.img_cafe)
            titulo = itemView.findViewById(R.id.titulo_row_cafe)
            btnEditar = itemView.findViewById(R.id.btnEditar_row_cafe)
            btnExcluir = itemView.findViewById(R.id.btnExluir_row_cafe)
        }
    }
}