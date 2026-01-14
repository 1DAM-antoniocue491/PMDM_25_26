package com.mm.ejercicio1

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var listViewNotas: ListView
    private lateinit var notas: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>

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

        listViewNotas = findViewById(R.id.listViewNotas)

        notas = mutableListOf(
            "Nota 1",
            "Nota 2"
        )

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            notas
        )
        listViewNotas.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_agregar -> {
                Toast.makeText(this, "Agregar tarea", Toast.LENGTH_SHORT).show()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.listNotas, CreateNote())
                fragmentTransaction.commit()
                true
            }
            R.id.action_eliminar_todas -> {
                Toast.makeText(this, "Eliminar todas", Toast.LENGTH_SHORT).show()
                eliminarTodasLasNotas()
                true
            }
            R.id.action_configuracion -> {
                Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun eliminarTodasLasNotas() {
        if (notas.isEmpty()) {
            Toast.makeText(this, "No hay tareas para eliminar", Toast.LENGTH_SHORT).show()
            return
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar")
        builder.setMessage("¿Eliminar todas las tareas?")

        builder.setPositiveButton("Sí") { _, _ ->
            notas.clear()
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Todas las tareas eliminadas", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No", null)
        builder.show()
    }
}