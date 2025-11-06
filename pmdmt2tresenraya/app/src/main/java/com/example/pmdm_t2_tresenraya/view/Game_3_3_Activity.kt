package com.example.pmdm_t2_tresenraya.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.R
import com.example.pmdm_t2_tresenraya.controller.CellState
import com.example.pmdm_t2_tresenraya.controller.Difficulty
import com.example.pmdm_t2_tresenraya.controller.IA
import com.example.pmdm_t2_tresenraya.controller.Play
import com.example.pmdm_t2_tresenraya.controller.Prefs
import com.example.pmdm_t2_tresenraya.controller.TTS

class Game_3_3_Activity : AppCompatActivity() {

    private lateinit var board: Array<CellState>
    private var isXTurn = true
    private var someoneWon = false
    private var aiEnabled = false

    private lateinit var play: Play
    private lateinit var tts: TTS
    private lateinit var prefs: Prefs
    private var difficulty: Difficulty = Difficulty.HARD
    private var iaLara: IA? = null

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

        prefs = Prefs.getInstance(this)
        tts = TTS.getInstance(this)
        play = Play(this)

        // Inicializamos tablero de 9 casillas (una sola dimensión)
        board = Array(9) { CellState.CLEAR }

        // Configurar modo IA
        aiEnabled = prefs.app.getGameMode() == "true"
        setupButtons()

        isXTurn = prefs.app.getStart()

        headerConfiguration()
    }

    private fun setupButtons() {
        for (row in 0..2) {
            for (col in 0..2) {
                val btnIdName = "celda${row + 1}${col + 1}"
                val btnId = resources.getIdentifier(btnIdName, "id", packageName)
                val btn: Button? = findViewById(btnId)
                play.setBaseColor(btn)
                btn?.setOnClickListener {
                    if (!someoneWon) onPlayerMove(row, col, btn)
                }
            }
        }
    }

    private fun onPlayerMove(row: Int, col: Int, btn: Button) {
        val index = row * 3 + col
        if (board[index] != CellState.CLEAR) return // Casilla ocupada

        // Marcar jugada del jugador
        if (isXTurn) {
            play.setX(btn.id, this)
            board[index] = CellState.CROSS
            headerConfiguration(CellState.CIRCLE)
        } else {
            play.setO(btn.id, this)
            board[index] = CellState.CIRCLE
            headerConfiguration(CellState.CROSS)
        }

        // Revisar victoria
        val winner = checkWinner()
        if (winner != null) {
            onGameEnd(winner)
            return
        }

        // Revisar empate
        if (board.all { it != CellState.CLEAR }) {
            onDraw()
            return
        }

        // Cambiar turno
        isXTurn = !isXTurn

        // Turno de IA si está activada y es su turno
        if (!isXTurn && aiEnabled) {
            iaLara = IA(
                board.copyOf(),
                playerSymbol = CellState.CROSS,
                aiSymbol = CellState.CIRCLE,
                isFirstMove = board.all { it == CellState.CLEAR }
            )

            val aiMove = iaLara!!.getMove(difficulty)
            if (aiMove != -1) aiTurn(aiMove)
        }
    }

    private fun aiTurn(aiMove: Int) {
        val row = aiMove / 3
        val col = aiMove % 3
        val btnIdName = "celda${row + 1}${col + 1}"
        val btnId = resources.getIdentifier(btnIdName, "id", packageName)
        val btn: Button? = findViewById(btnId)

        btn?.let {
            play.setO(btn.id, this)
            board[aiMove] = CellState.CIRCLE
        }

        val winner = checkWinner()
        if (winner != null) {
            onGameEnd(winner)
            return
        }

        if (board.all { it != CellState.CLEAR }) {
            onDraw()
            return
        }

        isXTurn = true
    }

    private fun checkWinner(): CellState? {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // filas
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // columnas
            listOf(0, 4, 8), listOf(2, 4, 6)                   // diagonales
        )

        for (pattern in winPatterns) {
            val a = board[pattern[0]]
            val b = board[pattern[1]]
            val c = board[pattern[2]]
            if (a != CellState.CLEAR && a == b && b == c) {
                return a
            }
        }
        return null
    }

    private fun onGameEnd(winner: CellState) {
        someoneWon = true

        val message = when {
            aiEnabled && winner == CellState.CIRCLE -> "La IA ha ganado."
            aiEnabled && winner == CellState.CROSS -> "¡Has ganado a la IA!"
            winner == CellState.CROSS -> "Gana el jugador 1"
            else -> "Gana el jugador 2"
        }

        tts.hablar(message, prefs.app.getLanguage())
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


        if (winner == CellState.CROSS && aiEnabled) {
            prefs.game.pvp.putGamesPlayed()
            prefs.game.iap.putWinPlayer()
        } else if (winner == CellState.CIRCLE && aiEnabled) {
            prefs.game.iap.putGamesPlayed()
            prefs.game.iap.putWinIA()
        } else if (winner == CellState.CROSS && !aiEnabled) {
            prefs.game.pvp.putGamesPlayed()
            prefs.game.pvp.putWinPlayer1()
        } else if (winner == CellState.CIRCLE && !aiEnabled) {
            prefs.game.pvp.putGamesPlayed()
            prefs.game.pvp.putWinPlayer2()
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun onDraw() {
        someoneWon = true
        tts.hablar("Habéis quedado en tablas", prefs.app.getLanguage())
        Toast.makeText(this, "¡Tablas!", Toast.LENGTH_SHORT).show()

        if (aiEnabled) {
            prefs.game.iap.putGamesPlayed()
            prefs.game.iap.putDraws()
        } else {
            prefs.game.pvp.putGamesPlayed()
            prefs.game.pvp.putDraws()
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun headerConfiguration(player: CellState = CellState.CROSS) {
        val header = findViewById<LinearLayout>(R.id.header_title)
        val cross = findViewById<AppCompatImageView>(R.id.cross)
        val circle = findViewById<AppCompatImageView>(R.id.circle)
        val player1 = findViewById<TextView>(R.id.player1)
        val player2 = findViewById<TextView>(R.id.player2)

        if (aiEnabled) {
            header.visibility = View.GONE
        }

        if (player == CellState.CROSS) {
            cross.imageTintList = ContextCompat.getColorStateList(this, R.color.red)
            player1.setTypeface(null, Typeface.BOLD)
            circle.imageTintList = ContextCompat.getColorStateList(this, R.color.blue_500)
        } else {
            cross.imageTintList = ContextCompat.getColorStateList(this, R.color.red_500)
            player2.setTypeface(null, Typeface.BOLD)
            circle.imageTintList = ContextCompat.getColorStateList(this, R.color.blue)
        }
    }
}
