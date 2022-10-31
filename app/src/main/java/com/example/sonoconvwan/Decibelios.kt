package com.example.sonoconvwan

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.text.DecimalFormat

class Decibelios : AppCompatActivity() {

    var sonido: MediaRecorder? = null
    private var ambiente = 0.0
    private val filtro = 0.6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decibelios)

        checkPermissions()

        var runner: Thread? = null

        val updater = Runnable { actualizarAudio() }

        val mHandler = Handler()

        if (runner == null) {
            runner = object : Thread() {
                override fun run() {
                    while (runner != null) {
                        try {
                            sleep(1500)
                            Log.i("Noise", "Tock")
                        } catch (e: InterruptedException) {
                        }
                        mHandler.post(updater)
                    }
                }
            }
            runner.start()
            Log.d("Noise", "start runner()")
        }

    }

    override fun onResume() {
        super.onResume()
        iniciarAudio()
    }

    override fun onPause() {
        super.onPause()
        detenerAudio()
    }

    fun iniciarAudio() {

        if (sonido == null) {
            sonido = MediaRecorder()
            sonido!!.setAudioSource(MediaRecorder.AudioSource.UNPROCESSED)
            sonido!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            sonido!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            sonido!!.setOutputFile("/dev/null")
            try {
                sonido!!.prepare()
            } catch (ioe: IOException) {
                Log.e(
                    "[MS]", "IOException: " +
                            Log.getStackTraceString(ioe)
                )
            } catch (e: SecurityException) {
                Log.e(
                    "[MS]", "SecurityException: " +
                            Log.getStackTraceString(e)
                )
            }
            try {
                sonido!!.start()
            } catch (e: SecurityException) {
                Log.e(
                    "[Monkey]", "SecurityException: " +
                            Log.getStackTraceString(e)
                )
            }

            //ambiente = 0.0;
        }
    }

    fun detenerAudio() {
        if (sonido != null) {
            sonido!!.stop()
            sonido!!.release()
            sonido=null
        }
    }

    @SuppressLint("SetTextI18n")
    fun actualizarAudio() {
        val df1 = DecimalFormat("####.0")
        val decib = sonidoDb(20.0)
        val decib1 = df1.format(decib)
        var sonidoDB=findViewById<TextView>(R.id.decibeles)
        var referencia=findViewById<TextView>(R.id.referencia)
        sonidoDB.setText(decib1 + " dB")
        if (decib < 10) {
            referencia.setText("Casi Silencio")
            sonidoDB.setTextColor(Color.rgb(0, 132, 194))
        } else if (decib < 20) {
            referencia.setText("Sonido Reloj")
            sonidoDB.setTextColor(Color.rgb(0, 190, 194))
        } else if (decib < 30) {
            referencia.setText("Susurro")
            sonidoDB.setTextColor(Color.rgb(0, 194, 121))
        } else if (decib < 40) {
            referencia.setText("Biblioteca. Canto de pájaros")
            sonidoDB.setTextColor(Color.rgb(0, 194, 63))
        } else if (decib < 50) {
            referencia.setText("Lluvia")
            sonidoDB.setTextColor(Color.rgb(34, 194, 0))
        } else if (decib < 60) {
            referencia.setText("Conversacion normal")
            sonidoDB.setTextColor(Color.rgb(114, 194, 5))
        } else if (decib < 70) {
            referencia.setText("Tráfico vehicular. Aspiradora")
            sonidoDB.setTextColor(Color.rgb(194, 187, 0))
        } else if (decib < 80) {
            referencia.setText("Musica alto volumen")
            sonidoDB.setTextColor(Color.rgb(233, 130, 0))
        } else if (decib < 90) {
            referencia.setText("Motor Diesel. Podadora de césped")
            sonidoDB.setTextColor(Color.rgb(233, 78, 0))
        } else if (decib < 100) {
            referencia.setText("Concierto")
            sonidoDB.setTextColor(Color.rgb(233, 27, 0))
        } else {
            referencia.setText("Umbral de dolor. Trueno")
            sonidoDB.setTextColor(Color.rgb(255, 0, 0))
        }
        val pascal = presionSonidouPA()
        val pascale = df1.format(pascal)
    }

    fun sonidoDb(ampl: Double): Double {
        val dbSPL = 20 * Math.log10(presionSonidouPA() / ampl)
        return if (dbSPL < 0) {
            return 0.0
        } else {
            return dbSPL
        }
    }

    fun sonidoAmbiente(): Double {
        return if (sonido != null){
            return (sonido!!.getMaxAmplitude().toDouble())
        } else{
            return 0.0
        }
    }

    fun presionSonidouPA(): Double {
        var amp : Double = sonidoAmbiente()
        ambiente=filtro*amp+(1.0-filtro)*ambiente
        return ambiente
    }

    private fun checkPermissions(){

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            requestMic()
        }else{

        }

    }

    private fun requestMic(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO)){

        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 777)

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==777){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }else{

            }
        }
    }
}