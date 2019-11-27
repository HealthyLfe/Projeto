package com.example.healthylife6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.healthylife6.adm.activity.AdmHomeActivity
import com.example.healthylife6.usuario.activity.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var email: String
    lateinit var senha: String

    lateinit var tvEsquecerSenha: TextView
    lateinit var addEmail: EditText
    lateinit var addSenha: EditText
    lateinit var btnLogin: Button
    lateinit var btnCdastrar: TextView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialize()
    }

    private fun initialize() {
        tvEsquecerSenha = esqueci_senha
        addEmail = email_login
        addSenha = senha_login
        btnLogin = btn_Login
        btnCdastrar = btn_Cadastrar

        auth = FirebaseAuth.getInstance()

        tvEsquecerSenha.setOnClickListener { startActivity(Intent(this, EsquecerSenhaActivity::class.java
                    )
                )
            }

        btnCdastrar.setOnClickListener { startActivity(Intent(this,CriarContaActivity::class.java
                    )
                )
            }

        btnLogin
            .setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        email = addEmail.text.toString()
        senha = addSenha.text.toString()

        if (email.isEmpty() && senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        } else {
            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        if (senha.equals("adm123")) {
                            val i = Intent(this, AdmHomeActivity::class.java)
                            startActivity(i)
                        } else {
                            val i = Intent(this, HomeActivity::class.java)
                            startActivity(i)
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Falha ao logar. Verifique seus dados novamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}


