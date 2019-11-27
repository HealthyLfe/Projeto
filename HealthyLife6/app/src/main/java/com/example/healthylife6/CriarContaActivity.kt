package com.example.healthylife6

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.healthylife6.classe.Usuario
import com.example.healthylife6.usuario.activity.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Button as Button

class CriarContaActivity : AppCompatActivity() {

    lateinit var addNome: EditText
    lateinit var addIdade: EditText
    lateinit var addPeso: EditText
    lateinit var addAltura: EditText
    lateinit var addEmail: EditText
    lateinit var addSenha: EditText
    lateinit var btnCriar: Button

    lateinit var mDatabaseReference: DatabaseReference
    lateinit var mDatabase: FirebaseDatabase
    lateinit var auth: FirebaseAuth

    lateinit var nome: String
    lateinit var idade: String
    lateinit var peso: String
    lateinit var altura: String
    lateinit var email: String
    lateinit var senha: String

    var sexoOp: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        initialize()
        init()
    }

    private fun initialize() {

        addNome = findViewById(R.id.txt_nome) as EditText
        addPeso = findViewById(R.id.txt_peso) as EditText
        addAltura = findViewById(R.id.txt_altura) as EditText
        addIdade = findViewById(R.id.txt_idade) as EditText
        addEmail = findViewById(R.id.txt_email) as EditText
        addSenha = findViewById(R.id.txt_senha) as EditText
        btnCriar = findViewById(R.id.btn_cad_user) as Button

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase.reference.child("Users")
        auth = FirebaseAuth.getInstance()

        btnCriar.setOnClickListener { criarNovaConta() }
    }

    private fun criarNovaConta() {

        nome = addNome.text.toString()
        idade = addIdade.text.toString()
        peso = addPeso.text.toString()
        altura = addAltura.text.toString()
        email = addEmail.text.toString()
        senha = addSenha.text.toString()

        if (nome.isEmpty() || sexoOp.isEmpty() || idade.isEmpty() || peso.isEmpty() || altura.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        } else {
            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        val userId = auth.currentUser!!.uid

                        val currentUserDb = mDatabaseReference.child(userId)

                        var user = Usuario(userId, nome, sexoOp, idade, peso, altura, email, senha )


                        currentUserDb.setValue(user)


                        Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT)
                            .show()

                        updateUserInfo()

                    } else {
                        Toast.makeText(this, "Cadastro não realizado", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    private fun updateUserInfo() {
        val i = Intent(this, HomeActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

    fun init() {

        val spinnerSexo: Spinner = findViewById(R.id.spinnerSexo)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this, R.array.sexo, android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerSexo.adapter = adapter
        }

        spinnerSexo.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //(parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#bdbdbd"))
                (parent.getChildAt(0) as TextView).setTypeface(Typeface.DEFAULT)
                sexoOp = spinnerSexo.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })
    }
}


