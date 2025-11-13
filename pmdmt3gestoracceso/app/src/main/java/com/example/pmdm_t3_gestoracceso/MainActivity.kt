package com.example.pmdm_t3_gestoracceso

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.FileNotFoundException
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private val fileName = "accesos.bin"
    private lateinit var recyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btnMostrar).setOnClickListener {
            mostrarRegistros()
        }

        registrarAcceso("ENTRADA")

    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStop() {
        super.onStop()
        registrarAcceso("SALIDA")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun registrarAcceso(tipo: String) {
        val ahora = LocalDateTime.now()
        val fecha = ahora.format(DateTimeFormatter.ofPattern("dd/MM/yy"))
        val hora = ahora.format(DateTimeFormatter.ofPattern("HH:mm"))

        try {
            openFileOutput(fileName, MODE_APPEND).use { fos ->
                DataOutputStream(fos).use { dos ->
                    dos.writeUTF(tipo)
                    dos.writeUTF(fecha)
                    dos.writeUTF(hora)
                }
            }
            Toast.makeText(this, "$tipo registrada", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(this, "Error al guardar el registro: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun mostrarRegistros() {
        val lista = mutableListOf<Registro>()
        try {
            openFileInput(fileName).use { fis ->
                DataInputStream(fis).use { dis ->
                    while (dis.available() > 0) {
                        val tipo = dis.readUTF()
                        val fecha = dis.readUTF()
                        val hora = dis.readUTF()
                        lista.add(Registro(tipo, fecha, hora))
                    }
                }
            }

            if (lista.isEmpty()) {
                Toast.makeText(this, "No hay registros todavía", Toast.LENGTH_SHORT).show()
            }

            recyclerView.adapter = RegistroAdapter(lista)

        } catch (e: FileNotFoundException) {
            Toast.makeText(this, "Aún no se ha creado ningún registro", Toast.LENGTH_SHORT).show()
        } catch (e: EOFException) {
            // Fin del archivo, normal
        } catch (e: IOException) {
            Toast.makeText(this, "Error al leer registros: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}