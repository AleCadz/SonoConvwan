package com.example.sonoconvwan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InicioSesion : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)
        setup()
    }

    private fun setup(){

        val btnInicio=findViewById<Button>(R.id.btnIniciar)
        val correo=findViewById<EditText>(R.id.correo)
        val contrasena=findViewById<EditText>(R.id.contrasena)

        btnInicio.setOnClickListener{
            if(correo.text.isNotEmpty() && contrasena.text.isNotEmpty()){

                FirebaseAuth.getInstance().signInWithEmailAndPassword(correo.text.toString(), contrasena.text.toString()).addOnCompleteListener{

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
        val correo=findViewById<EditText>(R.id.correo)
        val homeIntent = Intent(this, Menu::class.java)
        homeIntent.putExtra("correo", correo.text.toString())
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