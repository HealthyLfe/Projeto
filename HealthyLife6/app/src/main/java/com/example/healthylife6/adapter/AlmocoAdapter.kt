package com.example.healthylife6.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthylife6.R
import com.example.healthylife6.classe.idRecSelecionado
import com.example.healthylife6.model.ReceitaModel
import com.example.healthylife6.usuario.activity.HomeActivity
import com.example.healthylife6.usuario.fragment.DetalhesAlmocoFragment
import com.example.healthylife6.usuario.fragment.DetalhesCafeFragment
import com.squareup.picasso.Picasso

class AlmocoAdapter(private val context: Context, private val listAlmoco: ArrayList<ReceitaModel>): RecyclerView.Adapter<AlmocoAdapter.MyViewHolder>(){

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var(recId, titulo, ingr, desc, categoria, img) = listAlmoco[i]

        Picasso.get().load(listAlmoco[i].img).into(myViewHolder.img)
        myViewHolder.titulo.text = titulo

        myViewHolder.itemView.setOnClickListener {
            val fragment = DetalhesAlmocoFragment()
            val transaction = (context as HomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return listAlmoco.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.receitas_home_almoco, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var titulo: TextView

        init {
            img = itemView.findViewById(R.id.img_rec_almoco)
            titulo = itemView.findViewById(R.id.titulo_rec_almoco)
        }
    }
}