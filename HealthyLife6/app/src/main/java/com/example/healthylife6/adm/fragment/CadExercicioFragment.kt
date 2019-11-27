package com.example.healthylife6.adm.fragment


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.healthylife6.R
import com.example.healthylife6.model.ExercicioModel
import com.example.healthylife6.model.ReceitaModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_cad_exercicio.*
import kotlinx.android.synthetic.main.fragment_cad_receita.*
import kotlinx.android.synthetic.main.fragment_cad_receita.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CadExercicioFragment : Fragment() {

    lateinit var addTtulo: EditText
    lateinit var addTempo: EditText
    lateinit var addDesc: EditText
    lateinit var img: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cad_exercicio, container, false)

        addTtulo = view.findViewById(R.id.txt_titulo_exer)
        addTempo = view.findViewById(R.id.txt_tempo)
        addDesc = view.findViewById(R.id.txt_descricao_exer)

        val btnUpload_exer = view.findViewById<View>(R.id.btnUpload_exer)
        btnUpload_exer.setOnClickListener {
            Log.d("AdmCadExerFragment", "Clicou no botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnCancelar = view.findViewById<View>(R.id.btn_can_exer) as Button
        btnCancelar.setOnClickListener {
            val fragment = MenuExerFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnCadastrar = view.findViewById<View>(R.id.btn_cad_exer) as Button
        btnCadastrar.setOnClickListener {
            cadastrarExer()
            val fragment = ListaExercicioFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return view
    }

    var uriFotoSelecionada: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("AdmCadReceitaFragment", "Foto selecionada")

            uriFotoSelecionada = data.data

            Picasso.get().load(uriFotoSelecionada).resize(125,125).centerCrop().into(btnUpload_exer)
            btnUpload_exer.alpha = 0f

            uploadImagem()
        }
    }
    private fun uploadImagem() {
        if (uriFotoSelecionada == null) return
        val nomeImg = UUID.randomUUID().toString()
        Log.d("AdmCadExerFragment", "Nome img: $nomeImg")
        val ref = FirebaseStorage.getInstance().getReference("/exercicios/$nomeImg")

        ref.putFile(uriFotoSelecionada!!).addOnSuccessListener {
            Log.d("AdmCadExerFragment", "Imagem salva com sucesso: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("AdmCadExerFragment", "Location: $it")
                img = it.toString()
            }
        }
    }

    lateinit var exerId: String

    private fun cadastrarExer() {
        val titulo = addTtulo.text.toString().trim()
        val tempo = addTempo.text.toString().trim()
        val desc = addDesc.text.toString().trim()

        if (titulo.isEmpty() || tempo.isEmpty() || desc.isEmpty() || uriFotoSelecionada == null) {
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        } else {
            val ref = FirebaseDatabase.getInstance().getReference("exercicios")
            exerId = ref.push().key.toString()
            val exer = ExercicioModel(
                exerId,
                titulo,
                tempo,
                desc,
                img
            )
            ref.child(exerId).setValue(exer).addOnCompleteListener {
                Log.d("CadExerFragment", "Exercício cadastrado com sucesso!")
                Toast.makeText(
                    activity,
                    "Exercício cadastrado com sucesso!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}


