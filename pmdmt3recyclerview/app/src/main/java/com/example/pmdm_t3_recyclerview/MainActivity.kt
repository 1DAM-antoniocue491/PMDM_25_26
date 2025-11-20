package com.example.pmdm_t3_recyclerview

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private val clientes = mutableListOf<Cliente>()
    private lateinit var adapter: ClienteAdapter

    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
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

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etNIF = findViewById<EditText>(R.id.etNIF)
        val btnInsertar = findViewById<Button>(R.id.btnInsertar)
        val btnVaciar = findViewById<Button>(R.id.btnVaciar)
        val recycler = findViewById<RecyclerView>(R.id.recyclerClientes)

        adapter = ClienteAdapter(clientes) { cliente ->
            clientes.remove(cliente)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "${cliente.nombre} eliminado", Toast.LENGTH_SHORT).show()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnInsertar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val nif = etNIF.text.toString().trim().uppercase()

            if (nombre.isEmpty() || nif.isEmpty()) {
                Toast.makeText(this, "Nombre y NIF son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!NifUtils.validarNIF(nif)) {
                Toast.makeText(this, "NIF incorrecto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (clientes.any { it.nif == nif }) {
                Toast.makeText(this, "El NIF ya existe", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            clientes.add(Cliente(nombre, nif))
            clientes.sortBy { it.nif }
            adapter.notifyDataSetChanged()
            etNombre.text.clear()
            etNIF.text.clear()
        }

        btnVaciar.setOnClickListener {
            if (clientes.isEmpty()) {
                Toast.makeText(this, "La lista está vacía", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            exportarLista()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun exportarLista() {
        try {
            val fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"))
            val nombreArchivo = "clientes-$fecha.txt"
            val file = File(getExternalFilesDir(null), nombreArchivo)
            val writer = FileWriter(file)
            clientes.forEach {
                writer.write("${it.nombre};${it.nif}\n")
            }
            writer.close()
            clientes.clear()
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Lista exportada a ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error exportando la lista: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}