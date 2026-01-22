package com.example.pmdm_t4_agenda

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
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

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val lista = leerFichero("contactos.csv")
        val contactosOrdenados = lista.sortedBy { it.nombre }

        val adapter = ContactoAdapter(contactosOrdenados) { contacto ->
            AlertDialog.Builder(this)
                .setTitle(contacto.nombre)
                .setPositiveButton("Llamar") { dialog, _ ->
                    Toast.makeText(this, "Llamando a " + contacto.nombre, Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNeutralButton("Whatsapp") { dialog, _ ->
                    Toast.makeText(this, "Enviando whatsapp a " + contacto.nombre, Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    fun leerFichero(filename: String): List<Contacto> {
        // Abrir un fichero en modo lectura del flujo
        val fis: FileInputStream = this.openFileInput(filename)
        var list = mutableListOf<Contacto>()

        // Lectura del flujo
        var contenidoLeido = ""
        fis.use { input ->
            val iterador = input.bufferedReader().lineSequence().iterator()
            while (iterador.hasNext()) {
                val contacto = iterador.next()
                val linea = contacto.split(";")
                list.add(Contacto(linea[0], linea[1], linea[2]))
            }
        }
        Log.d("Lectura", "Contenido leído: $contenidoLeido")

        return list
    }
}