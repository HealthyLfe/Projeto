package com.example.healthylife6.adm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.healthylife6.R
import com.example.healthylife6.adm.fragment.*


import com.google.android.material.bottomnavigation.BottomNavigationView

class AdmHomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm_home)

        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener(this)

        //Instancia o Fragment que fica aberto por padrão
        val menuRecFragment = MenuRecFragment()
        //passa esse fragment por parametro
        abrirFragment(menuRecFragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId.equals(R.id.navigation_adm_receita)){
            val menuRecFragment = MenuRecFragment()
            abrirFragment(menuRecFragment)
            Toast.makeText(this, "Receitas", Toast.LENGTH_SHORT).show()
        }else{
            if(item.itemId.equals(R.id.navigation_adm_exercicio)) {
                Toast.makeText(this, "Exercícios", Toast.LENGTH_SHORT).show()
                val menuExerFragment = MenuExerFragment()
                abrirFragment(menuExerFragment)
            }else{
                    val admPerfilFragment = AdmPerfilFragment()
                    abrirFragment(admPerfilFragment)
                    Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()

                }
            }
        return true
    }

    private fun abrirFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerAdm, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}


