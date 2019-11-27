package com.example.healthylife6.adm.fragment


import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import com.example.healthylife6.R
import com.example.healthylife6.classe.categoriaAdm
import com.example.healthylife6.classe.idExercicioAdm
import com.example.healthylife6.classe.idReceitaAdm
import com.example.healthylife6.model.ExercicioModel
import com.example.healthylife6.model.ReceitaModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_editar_almoco.*
import kotlinx.android.synthetic.main.fragment_editar_exercicio.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class EditarExercicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_editar_exercicio, container, false)
        infoExercicio()

        val btnCancelar = view.findViewById<View>(R.id.can_edit_exer) as Button
        btnCancelar.setOnClickListener {
            val fragment = ListaExercicioFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnUpload = view.findViewById<View>(R.id.btnUpload_editar_exer) as Button
        btnUpload.setOnClickListener {
            Log.d("EdicaoExer", "Clicou botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnSalvar = view.findViewById<View>(R.id.btn_edit_exer) as Button
        btnSalvar.setOnClickListener {
            editar()
            val fragment = ListaExercicioFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return view
    }

    private fun editar() {
        val titulo = txt_titulo_edit_exer.text.toString().trim()
        val tempo = txt_tempo_edit_exer.text.toString().trim()
        val desc = txt_descricao_edit_exer.text.toString().trim()

        var imgNova: String

        if (titulo.isEmpty() || tempo.isEmpty() || desc.isEmpty()) {
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        } else {
            if (uriFotoSelecionada == null) {
                imgNova = imgAntiga

                val ref: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("exercicios").child(exerId)

                val exer = ExercicioModel(
                    exerId,
                    titulo,
                    tempo,
                    desc,
                    imgNova
                )
                ref.setValue(exer)
            } else {
                imgNova = url

                val ref: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("exercicios").child(exerId)

                val exer = ExercicioModel(
                    exerId,
                    titulo,
                    tempo,
                    desc,
                    imgNova
                )
                ref.setValue(exer)
            }
            Log.d("EdicaoExer", "Exercício atualizado com sucesso!")
            Toast.makeText(
                activity,
                "Exercício atualizado com sucesso!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    lateinit var exerId: String
    lateinit var imgAntiga: String

    private fun infoExercicio() {
        var ref = FirebaseDatabase.getInstance().getReference("exercicios").child(idExercicioAdm)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val exer = dataSnapshot!!.getValue(ExercicioModel::class.java)

                txt_titulo_edit_exer.setText(exer?.titulo)
                txt_titulo_edit_exer.setSelection(txt_titulo_edit_exer.text.length)

                txt_tempo_edit_exer.setText(exer?.tempo)
                txt_tempo_edit_exer.setSelection(txt_tempo_edit_exer.text.length)

                txt_descricao_edit_exer.setText(exer?.desc)
                txt_descricao_edit_exer.setSelection(txt_descricao_edit_exer.text.length)

                Picasso.get().load(exer?.img).into(img_edit_exer)

                exerId = exer?.exerId.toString()
                imgAntiga = exer?.img.toString()
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

    var uriFotoSelecionada: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("EdicaoExer", "Foto selecionada")

            uriFotoSelecionada = data.data
            // Log.d("EdicaoReceita", "URI: $uriFotoSelecionada")

            val imgNova = BitmapFactory.decodeFile(uriFotoSelecionada?.getPath())
            img_edit_exer.setImageBitmap(imgNova)

            btnUpload_editar_exer.alpha = 0f

            uploadImagem()
        }
    }

    lateinit var url: String

    private fun uploadImagem() {
        if (uriFotoSelecionada == null) return
        val nomeImg = UUID.randomUUID().toString()
        Log.d("EdicaoExer", "Nome img: $nomeImg")
        val ref = FirebaseStorage.getInstance().getReference("/exercicios/$nomeImg")

        ref.putFile(uriFotoSelecionada!!).addOnSuccessListener {
            Log.d("EdicaoExer", "Imagem salva com sucesso: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("EdicaoExer", "Location: $it")
                url = it.toString()
            }
        }
    }
}
