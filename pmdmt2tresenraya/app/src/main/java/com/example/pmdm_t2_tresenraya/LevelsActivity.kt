package com.example.pmdm_t2_tresenraya

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.model.Prefs

class LevelsActivity : AppCompatActivity() {
    val prefs: Prefs = Prefs.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_levels)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val container = findViewById<LinearLayout>(R.id.levelsContainer)

        // 🔹 Ejemplo: niveles desbloqueados (esto vendrá luego de prefs o base de datos)
        val unlockedLevels: Set<String> = prefs.app.getLevels() as Set<String>

        for (i in 1..30) {
            val itemView = layoutInflater.inflate(R.layout.item_level, container, false)

            val levelText = itemView.findViewById<TextView>(R.id.levelNumber)
            val btnPlay = itemView.findViewById<Button>(R.id.btnPlayLevel)

            levelText.text = "Nivel $i"

            // Mostrar botón solo si está desbloqueado
            if (unlockedLevels.contains(i.toString())) {
                btnPlay.visibility = View.VISIBLE
            }

            btnPlay.setOnClickListener {
                Toast.makeText(this, "Ver nivel $i", Toast.LENGTH_SHORT).show()
                // Aquí luego lanzaremos la partida correspondiente
            }

            container.addView(itemView)
        }
    }
}