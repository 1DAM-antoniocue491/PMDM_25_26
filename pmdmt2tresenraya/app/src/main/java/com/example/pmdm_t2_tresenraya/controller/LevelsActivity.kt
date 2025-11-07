package com.example.pmdm_t2_tresenraya.controller

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.R
import com.example.pmdm_t2_tresenraya.model.Play

class LevelsActivity : AppCompatActivity() {
    val play: Play = Play(this)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_levels)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val container = findViewById<LinearLayout>(R.id.containerLevels)
        val inflater = LayoutInflater.from(this)

        // Simulación de progreso por nivel (por ejemplo, guardado en Prefs o Controller)
        val progresoPorNivel = play.calcularProgresoPorNivel()

        for (nivel in 1..15) {
            // Inflamos el layout de cada nivel
            val itemView = inflater.inflate(R.layout.item_level, container, false)

            // Asignamos el número de nivel
            val tvLevel = itemView.findViewById<TextView>(R.id.tvLevelNumber)
            tvLevel.text = "Nivel $nivel"

            // Asignamos progreso (0–4)
            val progreso = progresoPorNivel.getOrElse(nivel - 1) { 0 }
            setLevelProgress(itemView, progreso)

            // Si el nivel está completado, añadimos un icono
            if (progreso >= 4) {
                val iconView = itemView.findViewById<ImageView>(R.id.ivCompleted)
                iconView.visibility = View.VISIBLE
            }

            // Finalmente, añadimos al contenedor
            container.addView(itemView)
        }
    }

    private fun setLevelProgress(view: View, progress: Int) {
        val segments = listOf(
            view.findViewById<View>(R.id.segment1),
            view.findViewById<View>(R.id.segment2),
            view.findViewById<View>(R.id.segment3),
            view.findViewById<View>(R.id.segment4)
        )

        segments.forEachIndexed { index, segment ->
            val drawableRes = if (index < progress)
                R.drawable.level_segment_filled
            else
                R.drawable.level_segment_unfilled
            segment.setBackgroundResource(drawableRes)
        }
    }
}