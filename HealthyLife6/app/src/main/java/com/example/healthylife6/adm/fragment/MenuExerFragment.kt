package com.example.healthylife6.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

import com.example.healthylife6.R
import kotlinx.android.synthetic.main.fragment_menu_exer.*

/**
 * A simple [Fragment] subclass.
 */
class MenuExerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_exer, container, false)

        val cardViewCadExer = view.findViewById<View>(R.id.cardViewCadExer) as CardView
        val cardViewListarExer = view.findViewById<View>(R.id.cardViewListarExer) as CardView

        cardViewCadExer.setOnClickListener {
            val fragment = CadExercicioFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        cardViewListarExer.setOnClickListener {
            val fragment = ListaExercicioFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }
}
