package com.example.healthylife6.usuario.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.healthylife6.R
import com.example.healthylife6.adapter.ListaCafeAdapter
import com.example.healthylife6.model.ReceitaModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_receitas_cafe.view.*

/**
 * A simple [Fragment] subclass.
 */
class ReceitasCafeFragment : Fragment() {

    private var listCafe = arrayListOf<com.example.healthylife6.model.ReceitaModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_receitas_cafe, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val cafe = FirebaseDatabase.getInstance().getReference("cafe da manha")

        val adapter = ListaCafeAdapter(activity!!, listCafe)
        var recyclerView = view.recyclerViewListaCafeUser
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        cafe.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listCafe.clear()

                    for (rec in dataSnapshot.children) {
                        val r = rec.getValue(ReceitaModel::class.java)
                        listCafe.add(r!!)
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )
    }
}