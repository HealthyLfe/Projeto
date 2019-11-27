package com.example.healthylife6.usuario.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.healthylife6.R
import com.example.healthylife6.usuario.fragment.ExerciciosFragment
import com.example.healthylife6.usuario.fragment.PerfilFragment
import com.example.healthylife6.usuario.fragment.ReceitasFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener(this)

        //Instancia o Fragment que fica aberto por padrão
        val receitasFragment = ReceitasFragment()
        //passa esse fragment por parametro
        abrirFragment(receitasFragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId.equals(R.id.navigation_receita)){
            val receitasFragment = ReceitasFragment()
            abrirFragment(receitasFragment)
            Toast.makeText(this, "Receitas", Toast.LENGTH_SHORT).show()
        }else{
            if(item.itemId.equals(R.id.navigation_exercicio)){
                Toast.makeText(this, "Exercícios", Toast.LENGTH_SHORT).show()
                val exerciciosFragment = ExerciciosFragment()
                abrirFragment(exerciciosFragment)
            }else{
                    val perfilFragment = PerfilFragment()
                    abrirFragment(perfilFragment)
                    Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()

                }
            }
        return true
    }

    private fun abrirFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

