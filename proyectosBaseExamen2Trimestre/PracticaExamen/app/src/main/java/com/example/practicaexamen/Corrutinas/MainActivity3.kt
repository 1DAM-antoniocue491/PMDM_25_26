package com.example.practicaexamen.Corrutinas

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaexamen.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity3 : AppCompatActivity() {
    private lateinit var textView: TextView
    private val scope = MainScope() // Para lanzar coroutines en el MainThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main4)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textView = findViewById(R.id.textView2)

        // Llamamos a la tarea asíncrona
        fetchData()
    }

    private fun fetchData() {
        scope.launch {
            textView.text = "Cargando..."

            // Simulamos una tarea pesada en background
            val result = withContext(Dispatchers.IO) {
                delay(2000) // Simula descarga o cálculo
                "Datos descargados"
            }

            // Actualizamos la UI
            textView.text = result
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel() // Evitar fugas de memoria
    }
}