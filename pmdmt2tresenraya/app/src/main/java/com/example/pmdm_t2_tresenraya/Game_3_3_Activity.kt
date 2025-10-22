package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.model.IA
import com.example.pmdm_t2_tresenraya.model.Play

class Game_3_3_Activity : AppCompatActivity() {

    private lateinit var game: Array<Array<Char>>
    private var isXTurn: Boolean = true
    private lateinit var play: Play
    private lateinit var ia: IA
    private var aiEnabled: Boolean = false

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

        // Inicializar objetos
        play = Play(this)
        ia = IA()
        game = Array(3) { Array(3) { ' ' } }

        // Configurar modo IA
        aiEnabled = intent.getStringExtra("ia") == "true"

        setupButtons()
    }

    private fun setupButtons() {
        for (row in 0..2) {
            for (col in 0..2) {
                val btnIdName = "celda${row + 1}${col + 1}"
                val btnId = resources.getIdentifier(btnIdName, "id", packageName)
                val btn: Button? = findViewById(btnId)
                btn?.setOnClickListener {
                    onPlayerMove(row, col, btn)
                }
            }
        }
    }

    private fun onPlayerMove(row: Int, col: Int, btn: Button) {
        if (game[row][col] != ' ') return // Casilla ocupada

        if (isXTurn) {
            play.setX(btn.id, game, row, col)
            game[row][col] = 'X'
        } else {
            play.setO(btn.id, game, row, col)
            game[row][col] = 'O'
        }

        Log.v("Game", gameContent())

        // Revisar si alguien ganó
        if (ia.checkWin(game)) {
            Log.v("Prueba", "¡Ganó ${if (isXTurn) "X" else "O"}!")
            return
        }

        isXTurn = !isXTurn

        // IA mueve si está activada y es su turno
        if (aiEnabled && !isXTurn) {
            val aiPos = ia.bestPosition(game)
            iaTurn(aiPos)
        }
    }

    private fun iaTurn(pos: Pair<Int, Int>) {
        val row = pos.first
        val col = pos.second
        val btnIdName = "celda${row + 1}${col + 1}"
        val btnId = resources.getIdentifier(btnIdName, "id", packageName)
        val btn: Button? = findViewById(btnId)

        btn?.let {
            play.setO(btn.id, game, row, col)
            game[row][col] = 'O'
            Log.v("Prueba", "IA movió:")
            Log.v("Prueba", gameContent())
        }

        // Revisar si IA ganó
        if (ia.checkWin(game)) {
            Log.v("Prueba", "¡Ganó la IA!")
            return
        }

        isXTurn = true
    }

    private fun gameContent(): String {
        return game.joinToString("\n") { it.joinToString(" ") }
    }
}
