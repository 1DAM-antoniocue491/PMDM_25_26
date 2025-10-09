package com.mm.pmdm_t2_reproductor

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private var clave = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)
    // Score general. Cada vez que se inicia la aplicación empieza a 0
    private var score: Int = 0
    private val TAG: String = "TECLA PULSADA: "

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.i("APP", "La app se ha iniciado correctamente")

        var claveUsuario = ArrayList<Int>()

        // BUTONS
        // Botón para la tecla Do Bajo
        val do_bajo = findViewById<Button>(R.id.do_bajo)
        do_bajo.setOnClickListener {
            score = addScore(score)

            if (comprobarClave(claveUsuario, 1)) {
                mediaPlayer = MediaPlayer.create(this, R.raw.soft)
            } else
                mediaPlayer = MediaPlayer.create(this, R.raw.do_bajo)

            mediaPlayer.start()

            Log.i(TAG, "Do Bajo")
            Toast.makeText(this, "$TAG Do Bajo", Toast.LENGTH_SHORT).show()
        }

        // Botón para la tecla Re
        val re = findViewById<Button>(R.id.re)
        re.setOnClickListener {
            score = addScore(score)

            if (comprobarClave(claveUsuario, 2))
                mediaPlayer = MediaPlayer.create(this, R.raw.soft)
            else
                mediaPlayer = MediaPlayer.create(this, R.raw.re)

            mediaPlayer.start()

            Log.i(TAG, "Re")
            Toast.makeText(this, "$TAG Re", Toast.LENGTH_SHORT).show()
        }

        // Botón para la tecla Mi
        val mi = findViewById<Button>(R.id.mi)
        mi.setOnClickListener {
            score = addScore(score)

            if (comprobarClave(claveUsuario, 3))
                mediaPlayer = MediaPlayer.create(this, R.raw.soft)
            else
                mediaPlayer = MediaPlayer.create(this, R.raw.mi)

            mediaPlayer.start()

            Log.i(TAG, "Mi")
            Toast.makeText(this, "$TAG Mi", Toast.LENGTH_SHORT).show()
        }

        // Botón para la tecla Fa
        val fa = findViewById<Button>(R.id.fa)
        fa.setOnClickListener {
            score = addScore(score)

            if (comprobarClave(claveUsuario, 4))
                mediaPlayer = MediaPlayer.create(this, R.raw.soft)
            else
                mediaPlayer = MediaPlayer.create(this, R.raw.fa)

            mediaPlayer.start()

            Log.i(TAG, "Fa")
            Toast.makeText(this, "$TAG Fa", Toast.LENGTH_SHORT).show()
        }

        // Botón para la tecla Sol
        val sol = findViewById<Button>(R.id.sol)
        sol.setOnClickListener {
            score = addScore(score)

            if (comprobarClave(claveUsuario, 5))
                mediaPlayer = MediaPlayer.create(this, R.raw.soft)
            else
                mediaPlayer = MediaPlayer.create(this, R.raw.sol)

            mediaPlayer.start()

            Log.i(TAG, "Sol")
            Toast.makeText(this, "$TAG Sol", Toast.LENGTH_SHORT).show()
        }

        // Botón para la tecla La
        val la = findViewById<Button>(R.id.la)
        la.setOnClickListener {
            score = addScore(score)

            if (comprobarClave(claveUsuario, 6))
                mediaPlayer = MediaPlayer.create(this, R.raw.soft)
            else
                mediaPlayer = MediaPlayer.create(this, R.raw.la)

            mediaPlayer.start()

            Log.i(TAG, "La")
            Toast.makeText(this, "$TAG La", Toast.LENGTH_SHORT).show()
        }

        // Botón para la tecla Si
        val si = findViewById<Button>(R.id.si)
        si.setOnClickListener {
            score = addScore(score)

            if (comprobarClave(claveUsuario, 7))
                mediaPlayer = MediaPlayer.create(this, R.raw.soft)
            else
                mediaPlayer = MediaPlayer.create(this, R.raw.si)

            mediaPlayer.start()

            Log.i(TAG, "Si")
            Toast.makeText(this, "$TAG Si", Toast.LENGTH_SHORT).show()
        }

        // Botón para la tecla Do Alto
        val do_alto = findViewById<Button>(R.id.do_alto)
        do_alto.setOnClickListener {
            score = addScore(score)

            if (comprobarClave(claveUsuario, 8))
                mediaPlayer = MediaPlayer.create(this, R.raw.soft)
            else
                mediaPlayer = MediaPlayer.create(this, R.raw.do_alto)

            mediaPlayer.start()

            Log.i(TAG, "Do Alto")
            Toast.makeText(this, "$TAG Do Alto", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addScore(score: Int) : Int {
        val text = findViewById<TextView>(R.id.score)
        var punt: Int = score + 1

        text.text = "Score: $punt"

        return punt
    }

    @SuppressLint("SetTextI18n")
    private fun comprobarClave(entrada: ArrayList<Int>, newNumber: Int): Boolean {
        var resultado = false

        if (entrada.size>=8) {
            entrada.removeAt(0)
            entrada.add(newNumber)
        } else{
            entrada.add(newNumber)
        }

        if (entrada == this.clave) {
            resultado = true
            val text = findViewById<TextView>(R.id.score)
            text.text = "Score: 0"
            this.score = 0
            Toast.makeText(this, "Clave correcta", Toast.LENGTH_SHORT).show()
            Log.i("INFORMACION", "Ejecución de la música lista")
        }

        return resultado
    }
}