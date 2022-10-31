package com.example.sonoconvwan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class Regis : AppCompatActivity() {

    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)
        setup()
    }

    private fun setup(){

        var btnRegis=findViewById<Button>(R.id.btnRegis)
        var correo=findViewById<EditText>(R.id.correo)
        var contrasena=findViewById<EditText>(R.id.contrasena)
        var nombre=findViewById<EditText>(R.id.nombre)
        var apellido=findViewById<EditText>(R.id.apellido)
        var ciudad=findViewById<EditText>(R.id.ciudad)
        var estado=findViewById<EditText>(R.id.estado)
        var pais=findViewById<EditText>(R.id.pais)

        btnRegis.setOnClickListener{

            db.collection("users").document(correo.text.toString()).set(hashMapOf("nombre" to nombre.text.toString(),
            "apellido" to apellido.text.toString(), "ciudad" to ciudad.text.toString(), "estado" to estado.text.toString(), "pais" to pais.text.toString(), "correo" to correo.text.toString()))

            if(correo.text.isNotEmpty() && contrasena.text.isNotEmpty()){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo.text.toString(), contrasena.text.toString()).addOnCompleteListener{

                    if(it.isSuccessful){
                        showHome()
                    }else{
                        showAlert()
                    }

                }

            }

        }

    }

    private fun showHome(){

        val homeIntent = Intent(this, fotoPerfil::class.java)
        startActivity(homeIntent)

    }

    private fun showAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando el usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

}