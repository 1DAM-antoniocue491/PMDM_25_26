package com.example.pmdm_t2_tresenraya.controller

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.R
import com.example.pmdm_t2_tresenraya.model.CellState
import com.example.pmdm_t2_tresenraya.model.Difficulty
import com.example.pmdm_t2_tresenraya.model.IA
import com.example.pmdm_t2_tresenraya.model.Play
import com.example.pmdm_t2_tresenraya.model.Prefs
import com.example.pmdm_t2_tresenraya.model.TTS

class Game_3_3_Activity : AppCompatActivity() {
    private var isXTurn = true
    private var someoneWon = false
    private var aiEnabled = false

    private lateinit var play: Play
    private val prefs: Prefs = Prefs.getInstance(this)
    private lateinit var difficulty: Difficulty

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game33)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (prefs.game.iap.getPoints().div(4) <= 5)
            difficulty = Difficulty.EASY
        else if (prefs.game.iap.getPoints().div(4) <= 10) {
            val x = (1..5).random()
            if (x == 1) {
                difficulty = Difficulty.EASY
            } else {
                difficulty = Difficulty.MEDIUM
            }
        } else {
            val x = (1..3).random()
            if (x == 1) {
                difficulty = Difficulty.MEDIUM
            } else {
                difficulty = Difficulty.HARD
            }
        }

        // Configurar modo IA
        aiEnabled = prefs.app.getGameMode() == "true"

        play = Play(this, aiEnabled, isXTurn, difficulty)

        setupButtons()

        isXTurn = prefs.app.getStart()

        play.headerConfiguration()
    }

    fun setupButtons() {
        for (row in 0..2) {
            for (col in 0..2) {
                val btnIdName = "celda${row + 1}${col + 1}"
                val btnId = resources.getIdentifier(btnIdName, "id", packageName)
                val btn: Button = findViewById(btnId)
                play.setBaseColor(btn)
                btn.setOnClickListener {
                    if (!someoneWon) play.onPlayerMove(row, col, btn)
                }
            }
        }
    }
}
