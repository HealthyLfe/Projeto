package com.example.healthylife6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class EsquecerSenhaActivity : AppCompatActivity() {

    private val TAG = "EsquecerSenhaActivity"
    val message = "Email enviado"
    val message2 = "Email não enviado"

    lateinit var addEmail: EditText
    lateinit var btnRec: Button

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esquecer_senha)

        initialize()
    }

    private fun initialize(){
        addEmail = findViewById(R.id.txt_email) as EditText
        btnRec = findViewById<Button>(R.id.btn_rec) as Button

        mAuth = FirebaseAuth.getInstance()

        btnRec.setOnClickListener{sendPasswordEmail()}
    }

    private fun sendPasswordEmail(){
        val email = addEmail.text.toString()

        if(!TextUtils.isEmpty(email)){
            mAuth
                .sendPasswordResetEmail(email)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        updateUI()
                    } else {
                        Log.e(TAG, "não enviado", task.exception)
                        Toast.makeText(this, "NENHUM USER ENCONTRADO COM ESSE EMAIL", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "ENTRE COM UM EMAIL VÁLIDO", Toast.LENGTH_LONG).show()

        }
    }

    private fun updateUI(){
        val intent = Intent(this@EsquecerSenhaActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
