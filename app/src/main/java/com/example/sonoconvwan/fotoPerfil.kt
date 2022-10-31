package com.example.sonoconvwan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore

class fotoPerfil : AppCompatActivity() {

    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto_perfil)

        var btnContinuar = findViewById<Button>(R.id.btnContinuar)
        var usuario = findViewById<EditText>(R.id.usuario)

        btnContinuar.setOnClickListener{

            db.collection("users").document(getIntent().getStringExtra("correo").toString()).get().addOnSuccessListener {

                db.collection("users").document(getIntent().getStringExtra("correo").toString()).set(hashMapOf("usuario" to usuario.text.toString(), "nombre" to it.get("nombre") as String?,
                    "apellido" to it.get("apellido") as String?, "ciudad" to it.get("ciudad") as String?, "estado" to it.get("estado") as String?,
                    "pais" to it.get("pais") as String?, "correo" to it.get("correo") as String?))

            }

            val intent = Intent(this, Decibelios::class.java)
            startActivity(intent)

        }

    }
}