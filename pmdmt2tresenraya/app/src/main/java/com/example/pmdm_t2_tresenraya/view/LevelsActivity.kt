package com.example.pmdm_t2_tresenraya.view

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.R
import com.example.pmdm_t2_tresenraya.controller.Play
import com.example.pmdm_t2_tresenraya.controller.Prefs

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

        val playClass = Play(this)

        val container = findViewById<LinearLayout>(R.id.levelsContainer)

        val unlockedLevels: Set<String> = prefs.app.getLevels() as Set<String>

        for (i in 1..30) {
            val itemView = layoutInflater.inflate(R.layout.item_level, container, false)

            playClass.styleButton(itemView, this)

            val levelText = itemView.findViewById<TextView>(R.id.levelNumber)
            levelText.text = "Nivel $i"

            container.addView(itemView)
        }
    }
}