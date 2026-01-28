package com.example.notasapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import java.io.File
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

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ListFragment())
            .commit()
    }

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.newTask -> {
                changeFragment(CreateDetailFragment())
                true
            }
            R.id.deleteAll -> {
                Toast.makeText(this, "Eliminar todo", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.info -> {
                Toast.makeText(this, "Info de la app", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun leerFichero(filename: String): List<Nota> {
        // Abrir un fichero en modo lectura del flujo
        val fis: FileInputStream = this.openFileInput(filename)
        var list = mutableListOf<Nota>()

        // Lectura del flujo
        var contenidoLeido = ""
        fis.use { input ->
            val iterador = input.bufferedReader().lineSequence().iterator()
            while (iterador.hasNext()) {
                val contacto = iterador.next()
                val linea = contacto.split(";")
                list.add(Nota(linea[0], linea[1], linea[2], Prioridad.valueOf(linea[3]), linea[4].toLong()))
            }
        }
        Log.d("Lectura", "Contenido leído: $contenidoLeido")

        return list
    }

    fun escribirFichero(filename: String, contenido: String) {
        // Abrir un fichero en modo escritura del flujo (privado a la aplicación)
        val fos: FileOutputStream = this.openFileOutput(filename, Context.MODE_PRIVATE)

        // Escritura del flujo
        fos.use {
            it.write("$contenido\n".toByteArray())
        }
    }

    fun copyFileToInternalStorage(fileName: String) {
        try {
            val file = File(filesDir, fileName)

            // Solo copiar si no existe
            if (!file.exists()) {
                // Leer desde res/raw o assets
                val inputStream = assets.open(fileName)
                val outputStream = openFileOutput(fileName, MODE_PRIVATE)

                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error copying file: ${e.message}")
        }
    }
}