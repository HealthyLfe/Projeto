package com.example.healthylife6.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.healthylife6.R
import com.example.healthylife6.adapter.ListaJantaAdapter
import com.example.healthylife6.model.ReceitaModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_lista_janta.view.*

/**
 * A simple [Fragment] subclass.
 */
class ListaJantaFragment : Fragment() {

    private var listJanta = arrayListOf<com.example.healthylife6.model.ReceitaModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_lista_janta, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val janta = FirebaseDatabase.getInstance().getReference("jantar")

        val adapter = ListaJantaAdapter(activity!!, listJanta)
        var recyclerView = view.recyclerViewListaReceitaJanta
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        janta.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listJanta.clear()

                    for (rec in dataSnapshot.children) {
                        val r = rec.getValue(ReceitaModel::class.java)
                        listJanta.add(r!!)
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )
    }
}