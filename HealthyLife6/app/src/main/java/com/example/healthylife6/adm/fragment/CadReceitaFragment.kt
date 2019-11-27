package com.example.healthylife6.adm.fragment


import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.healthylife6.R
import com.example.healthylife6.model.ReceitaModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_cad_receita.*
import kotlinx.android.synthetic.main.fragment_cad_receita.view.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class CadReceitaFragment : Fragment() {

    lateinit var addTtulo: EditText
    lateinit var addIngred: EditText
    lateinit var addDesc: EditText
    lateinit var categoria: String
    lateinit var img: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cad_receita, container, false)

        addTtulo = view.findViewById(R.id.txt_titulo)
        addIngred = view.findViewById(R.id.txt_ingred)
        addDesc = view.findViewById(R.id.txt_descricao)

        val categorias = arrayOf(
            "",
            "Café da Manhã",
            "Almoço",
            "Jantar"
        )
        view.spinnerCat.adapter = ArrayAdapter<String>(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            categorias
        )
        view.spinnerCat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(
                    activity, "Selecione uma categoria!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                categoria = categorias.get(p2)
                Log.d("AdmCadReceitaFragment", "Categoria: $categoria")
            }
        }

        val btnUpload = view.findViewById<View>(R.id.btnUpload_rec) as Button
        btnUpload.setOnClickListener {
            Log.d("AdmCadReceitaFragment", "Clicou no botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnCancelar = view.findViewById<View>(R.id.btn_can_rec) as Button
        btnCancelar.setOnClickListener {
            val fragment = MenuRecFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnCadastrar = view.findViewById<View>(R.id.btn_cad_rec) as Button
        btnCadastrar.setOnClickListener {
            cadastrarReceita()
            listar()
        }
        return view
    }

    var uriFotoSelecionada: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("AdmCadReceitaFragment", "Foto selecionada")

            uriFotoSelecionada = data.data

            Log.d("AdmCadReceitaFragment", "URI: $uriFotoSelecionada")

            btnUpload_rec.alpha = 0f

            uploadImagem()
        }
    }

    private fun uploadImagem() {
        if (uriFotoSelecionada == null) return
        val nomeImg = UUID.randomUUID().toString()
        Log.d("AdmCadReceitaFragment", "Nome img: $nomeImg")
        val ref = FirebaseStorage.getInstance().getReference("/receitas/$nomeImg")

        ref.putFile(uriFotoSelecionada!!).addOnSuccessListener {
            Log.d("AdmCadReceitaFragment", "Imagem salva com sucesso: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("AdmCadReceitaFragment", "Location: $it")
                img = it.toString()
            }
        }
    }

    lateinit var recId: String

    private fun cadastrarReceita() {
        val titulo = addTtulo.text.toString().trim()
        val ingr = addIngred.text.toString().trim()
        val desc = addDesc.text.toString().trim()

        if (titulo.isEmpty() || ingr.isEmpty() || desc.isEmpty() || categoria.isEmpty() || uriFotoSelecionada == null) {
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        } else {
            if (categoria.equals("Café da Manhã")) {
                val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("cafe da manha")
                recId = ref.push().key.toString()
                val receita = ReceitaModel(
                    recId,
                    titulo,
                    ingr,
                    desc,
                    categoria,
                    img
                )
                ref.child(recId).setValue(receita).addOnCompleteListener {
                    Log.d("CadReceitaFragment", "Receita cadastrada com sucesso!")
                    Toast.makeText(
                        activity,
                        "Receita cadastrada com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                if (categoria.equals("Almoço")) {
                    val ref = FirebaseDatabase.getInstance().getReference("almoco")
                    recId = ref.push().key.toString()
                    val receita = ReceitaModel(
                        recId,
                        titulo,
                        ingr,
                        desc,
                        categoria,
                        img
                    )
                    ref.child(recId).setValue(receita).addOnCompleteListener {
                        Log.d("CadReceitaFragment", "Receita cadastrada com sucesso!")
                        Toast.makeText(
                            activity,
                            "Receita cadastrada com sucesso!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    val ref = FirebaseDatabase.getInstance().getReference("jantar")
                    recId = ref.push().key.toString()
                    val receita = ReceitaModel(
                        recId,
                        titulo,
                        ingr,
                        desc,
                        categoria,
                        img
                    )
                    ref.child(recId).setValue(receita).addOnCompleteListener {
                        Log.d("CadReceitaFragment", "Receita cadastrada com sucesso!")
                        Toast.makeText(
                            activity,
                            "Receita cadastrada com sucesso!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    fun listar(){
        if (categoria.equals("Café da Manhã")) {
            val fragment = ListaCafeFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        } else {
            if (categoria.equals("Almoço")) {
                val fragment = ListaAlmocoFragment()
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.containerAdm, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            } else {
                if (categoria.equals("Jantar")) {
                    val fragment = ListaJantaFragment()
                    val fragmentManager = activity!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.containerAdm, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
        }
    }
}