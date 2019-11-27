package com.example.healthylife6.usuario.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.healthylife6.R
import com.example.healthylife6.adapter.AlmocoAdapter
import com.example.healthylife6.adapter.CafeAdapter
import com.example.healthylife6.adapter.JantaAdapter
import com.example.healthylife6.model.ReceitaModel

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_lista_almoco.view.recyclerViewListaReceitaAlmoco
import kotlinx.android.synthetic.main.fragment_lista_cafe.view.recyclerViewListaReceitaCafe
import kotlinx.android.synthetic.main.fragment_lista_janta.view.recyclerViewListaReceitaJanta

/**
 * A simple [Fragment] subclass.
 */
class ReceitasFragment : Fragment() {

    private var listCafe = arrayListOf<com.example.healthylife6.model.ReceitaModel>()
    private var listAlmoco = arrayListOf<com.example.healthylife6.model.ReceitaModel>()
    private var listJanta = arrayListOf<com.example.healthylife6.model.ReceitaModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_receitas, container, false)
        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val cafe = FirebaseDatabase.getInstance().getReference("cafe da manha")

        val adapter = CafeAdapter(activity!!, listCafe)
        var recyclerView = view.recyclerViewListaReceitaCafe
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
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

        val almoco = FirebaseDatabase.getInstance().getReference("almoco")

        val adapter2 = AlmocoAdapter(activity!!, listAlmoco)
        var recyclerView2 = view.recyclerViewListaReceitaAlmoco
        recyclerView2.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView2.adapter = adapter2

        almoco.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listAlmoco.clear()

                    for (alm in dataSnapshot.children) {
                        val s = alm.getValue(ReceitaModel::class.java)
                        listAlmoco.add(s!!)
                    }

                    adapter2.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )

        val janta = FirebaseDatabase.getInstance().getReference("jantar")

        val adapter3 = JantaAdapter(activity!!, listJanta)
        var recyclerView3 = view.recyclerViewListaReceitaJanta
        recyclerView3.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        recyclerView3.adapter = adapter3

        janta.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listJanta.clear()

                    for (jan in dataSnapshot.children) {
                        val t = jan.getValue(ReceitaModel::class.java)
                        listJanta.add(t!!)
                    }

                    adapter3.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )

    }
}
