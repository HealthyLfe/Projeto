package com.example.healthylife6.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.healthylife6.R
import com.example.healthylife6.adapter.ListaAlmocoAdapter
import com.example.healthylife6.model.ReceitaModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_lista_almoco.view.*

/**
 * A simple [Fragment] subclass.
 */
class ListaAlmocoFragment : Fragment() {

    private var listAlmoco = arrayListOf<com.example.healthylife6.model.ReceitaModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_lista_almoco, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val almoco = FirebaseDatabase.getInstance().getReference("almoco")

        val adapter = ListaAlmocoAdapter(activity!!, listAlmoco)
        var recyclerView = view.recyclerViewListaReceitaAlmoco
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        almoco.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listAlmoco.clear()

                    for (rec in dataSnapshot.children) {
                        val r = rec.getValue(ReceitaModel::class.java)
                        listAlmoco.add(r!!)
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )
    }
}