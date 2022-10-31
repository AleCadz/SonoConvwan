package com.example.sonoconvwan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnIniciar=findViewById<Button>(R.id.btnIniciar)
        var btnRegis=findViewById<Button>(R.id.btnRegistrar)

        btnIniciar.setOnClickListener{

            val intent = Intent(this, InicioSesion::class.java)
            startActivity(intent)

        }

        btnRegis.setOnClickListener {

            val intent = Intent(this, Regis::class.java)
            startActivity(intent)

        }
    }
}