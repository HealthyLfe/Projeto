package com.example.healthylife6.usuario.fragment


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
import kotlinx.android.synthetic.main.fragment_receitas_jantar.view.*

/**
 * A simple [Fragment] subclass.
 */
class ReceitasJantarFragment : Fragment() {

    private var listJantar = arrayListOf<com.example.healthylife6.model.ReceitaModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_receitas_jantar, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val jantar = FirebaseDatabase.getInstance().getReference("jantar")

        val adapter = ListaJantaAdapter(activity!!, listJantar)
        var recyclerView = view.recyclerViewListaJantaUser
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        jantar.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listJantar.clear()

                    for (rec in dataSnapshot.children) {
                        val r = rec.getValue(ReceitaModel::class.java)
                        listJantar.add(r!!)
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )
    }
}