package com.example.healthylife6.adm.fragment


import com.example.healthylife6.adm.fragment.ListaAlmocoFragment
import com.example.healthylife6.adm.fragment.ListaCafeFragment
import com.example.healthylife6.adm.fragment.ListaJantaFragment

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
import com.example.healthylife6.classe.idReceitaAdm
import com.example.healthylife6.model.ReceitaModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_editar_jantar.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class EditarJantarFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_editar_jantar, container, false)

        infoReceita()

        val btnCancelar = view.findViewById<View>(R.id.can_edit_rec_jantar) as Button
        btnCancelar.setOnClickListener {
            val fragment = ListaJantaFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnUpload = view.findViewById<View>(R.id.btnUpload_editar_jantar) as Button
        btnUpload.setOnClickListener {
            Log.d("EdicaoReceita", "Clicou botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnSalvar = view.findViewById<View>(R.id.btn_edit_rec_jantar) as Button
        btnSalvar.setOnClickListener {
            editar()
            val fragment = ListaJantaFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return view
    }

    lateinit var recId: String
    lateinit var categoria: String
    lateinit var imgAntiga: String

    private fun infoReceita() {
        lateinit var ref: DatabaseReference

        if (categoriaAdm.equals("Café da Manhã")) {
            Log.d("EditarReceitaAlmoco", "café")
            ref = FirebaseDatabase.getInstance().getReference("cafe da manha").child(idReceitaAdm)
        } else {
            if (categoriaAdm.equals("Almoço")) {
                Log.d("EditarReceitaAlmoco", "almoço")
                ref = FirebaseDatabase.getInstance().getReference("almoco").child(idReceitaAdm)
            } else {
                Log.d("EditarReceitaAlmoco", "jantar")
                ref = FirebaseDatabase.getInstance().getReference("jantar").child(idReceitaAdm)

            }
        }
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val rec = dataSnapshot!!.getValue(ReceitaModel::class.java)
                txt_titulo_edit_jantar.setText(rec?.titulo)
                txt_titulo_edit_jantar.setSelection(txt_titulo_edit_jantar.text.length)

                txt_ingred_edit_jantar.setText(rec?.ingrediente)
                txt_ingred_edit_jantar.setSelection(txt_ingred_edit_jantar.text.length)

                txt_descricao_edit_jantar.setText(rec?.desc)
                txt_descricao_edit_jantar.setSelection(txt_descricao_edit_jantar.text.length)

                Picasso.get().load(rec?.img).into(img_edit_jantar)

                recId = rec?.recId.toString()
                categoria = rec?.categoria.toString()
                imgAntiga = rec?.img.toString()
            }

        })
    }

    private fun editar() {
        val titulo = txt_titulo_edit_jantar.text.toString().trim()
        val ingrediente = txt_ingred_edit_jantar.text.toString().trim()
        val desc = txt_descricao_edit_jantar.text.toString().trim()

        var imgNova: String

        if (titulo.isEmpty() || ingrediente.isEmpty() || desc.isEmpty()) {
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        } else {
            if (uriFotoSelecionada == null) {
                lateinit var ref: DatabaseReference

                if (categoriaAdm.equals("Café da Manhã")) {
                    Log.d("EditarReceitaJantar", "CAFÉ")
                    ref = FirebaseDatabase.getInstance().getReference("cafe da manha").child(recId)
                } else {
                    if (categoriaAdm.equals("Almoço")) {
                        Log.d("EditarReceitaJantar", "ALMOÇO")
                        ref = FirebaseDatabase.getInstance().getReference("almoco").child(recId)
                    } else {
                        Log.d("EditarReceitaJantar", "JANTAR")
                        ref = FirebaseDatabase.getInstance().getReference("jantar").child(recId)
                    }
                }

                imgNova = imgAntiga

                val receita = ReceitaModel(
                    recId,
                    titulo,
                    ingrediente,
                    desc,
                    categoria,
                    imgNova
                )
                ref.setValue(receita)
            } else {

                lateinit var ref: DatabaseReference

                if (categoriaAdm.equals("Café da Manhã")) {
                    Log.d("EditarReceita", "café")
                    ref = FirebaseDatabase.getInstance().getReference("cafe da manha").child(recId)
                } else {
                    if (categoriaAdm.equals("Almoço")) {
                        Log.d("EditarReceita", "almoço")
                        ref = FirebaseDatabase.getInstance().getReference("almoco").child(recId)
                    } else {
                        Log.d("EditarReceita", "jantar")
                        ref = FirebaseDatabase.getInstance().getReference("jantar").child(recId)
                    }
                }
                imgNova = url

                val receita = ReceitaModel(
                    recId,
                    titulo,
                    ingrediente,
                    desc,
                    categoria,
                    imgNova
                )
                ref.setValue(receita)
            }
            Log.d("EdicaoReceita", "Receita atualizada com sucesso!")
            Toast.makeText(
                activity,
                "Receita atualizada com sucesso!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    var uriFotoSelecionada: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("EdicaoReceita", "Foto selecionada")

            uriFotoSelecionada = data.data
            // Log.d("EdicaoReceita", "URI: $uriFotoSelecionada")

            val imgNova = BitmapFactory.decodeFile(uriFotoSelecionada?.getPath())
            img_edit_jantar.setImageBitmap(imgNova)

            btnUpload_editar_jantar.alpha = 0f

            uploadImagem()
        }
    }

    lateinit var url: String

    private fun uploadImagem() {
        if (uriFotoSelecionada == null) return
        val nomeImg = UUID.randomUUID().toString()
        Log.d("EdicaoReceita", "Nome img: $nomeImg")
        val ref = FirebaseStorage.getInstance().getReference("/receitas/$nomeImg")

        ref.putFile(uriFotoSelecionada!!).addOnSuccessListener {
            Log.d("EdicaoReceita", "Imagem salva com sucesso: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("EdicaoReceita", "Location: $it")
                url = it.toString()
            }
        }
    }
}


