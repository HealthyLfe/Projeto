package com.example.healthylife6.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

import com.example.healthylife6.R

/**
 * A simple [Fragment] subclass.
 */
class ListarReceitaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_listar_receita, container, false)

        val cardViewCafeAdm = view.findViewById<View>(R.id.cardViewCafeAdm) as CardView
        val cardViewAlmocoAdm = view.findViewById<View>(R.id.cardViewAlmocoAdm) as CardView
        val cardviewJantaAdm = view.findViewById<View>(R.id.cardViewJantaAdm) as CardView

        cardViewCafeAdm.setOnClickListener {
            val fragment = ListaCafeFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        cardViewAlmocoAdm.setOnClickListener {
            val fragment = ListaAlmocoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        cardviewJantaAdm.setOnClickListener {
            val fragment = ListaJantaFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }
}