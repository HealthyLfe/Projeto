package com.example.healthylife6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeToLogin()
           /* teste.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)*/
        }
    fun changeToLogin() {
        val intent = Intent(this, LoginActivity::class.java)

        Handler().postDelayed({
            intent.change()
        }, 5000)
    }

    fun Intent.change() {
        startActivity(this)
        finish()
    }

}


