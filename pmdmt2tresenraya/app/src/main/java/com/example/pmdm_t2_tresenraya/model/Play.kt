package com.example.pmdm_t2_tresenraya.model

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.pmdm_t2_tresenraya.controller.MainActivity
import com.google.android.material.R

class Play (
    private val activity: Activity,
    private val aiEnabled: Boolean = false,
    private var isXTurn: Boolean = true,
    private val difficulty: Difficulty = Difficulty.EASY
    ) {
    private val prefs = Prefs.getInstance(activity)
    private val plus = Prefs.GamePrefs.IA.Modifier.PLUS
    private val less = Prefs.GamePrefs.IA.Modifier.LESS
    private val equals = Prefs.GamePrefs.IA.Modifier.EQUALS
    private val tts: TTS = TTS.getInstance(activity)
    private lateinit var ia: IA
    private var someoneWon: Boolean
        get() {
            TODO()
        }
        set(value) {}
    private val board:Array<CellState> = Array(9) { CellState.CLEAR }

    fun setBaseColor(celda: Button) {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
        val colorFromTheme = typedValue.data

        celda.setBackgroundColor(colorFromTheme)
    }

    fun styleButton(buttons: Array<Button>, context: Context) {
        val color = prefs.app.getStyle(context)

        for (btn in buttons) {
            btn.setBackgroundColor(color)
        }
    }

    fun styleButton(button: Button, context: Context) {
        val color = prefs.app.getStyle(context)

        button.setBackgroundColor(color)
    }

    fun setX(btn_id: Int, context: Context) {
        val btn: Button = activity.findViewById(btn_id)
        btn.background = ContextCompat.getDrawable(context, com.example.pmdm_t2_tresenraya.R.drawable.x_symbol)
        btn.backgroundTintList = null
    }

    fun setO(btn_id: Int, context: Context) {
        val btn: Button = activity.findViewById(btn_id)
        btn.background = ContextCompat.getDrawable(context, com.example.pmdm_t2_tresenraya.R.drawable.circle)
        btn.backgroundTintList = null
    }

    fun calcularProgresoPorNivel(): List<Int> {
        val niveles = 15
        val pasosPorNivel = 4
        val progresoPorNivel = mutableListOf<Int>()

        var puntosRestantes = prefs.game.iap.getPoints()

        repeat(niveles) {
            val completado = if (puntosRestantes >= pasosPorNivel) {
                pasosPorNivel
            } else {
                puntosRestantes.coerceAtLeast(0)
            }
            progresoPorNivel.add(completado)
            puntosRestantes -= pasosPorNivel
        }

        return progresoPorNivel
    }

    fun onPlayerMove(row: Int, col: Int, btn: Button) {
        val index = row * 3 + col
        if (board[index] != CellState.CLEAR) return // Casilla ocupada

        // Marcar jugada del jugador
        if (isXTurn) {
            setX(btn.id, activity)
            board[index] = CellState.CROSS
            headerConfiguration(CellState.CIRCLE)
        } else {
            setO(btn.id, activity)
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
            ia = IA(
                board.copyOf(),
                playerSymbol = CellState.CROSS,
                aiSymbol = CellState.CIRCLE,
                isFirstMove = board.all { it == CellState.CLEAR }
            )

            val aiMove = ia.getMove(difficulty)
            if (aiMove != -1) aiTurn(aiMove)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun aiTurn(aiMove: Int) {
        val row = aiMove / 3
        val col = aiMove % 3
        val btnIdName = "celda${row + 1}${col + 1}"
        val btnId = activity.resources.getIdentifier(btnIdName, "id", activity.packageName)
        val btn: Button? = activity.findViewById(btnId)

        btn?.let {
            setO(btn.id, activity)
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

        when {
            aiEnabled && winner == CellState.CIRCLE -> {
                mensaje("La IA ha ganado. Oo, has bajado puntos en el nivel")
            }
            aiEnabled && winner == CellState.CROSS -> {
                mensaje("¡Has ganado a la IA! Bien! Has subido puntos en tu nivel")
            }
            winner == CellState.CROSS -> {
                mensaje("Gana el jugador 1")
            }
            else -> {
                mensaje("Gana el jugador 2")
            }
        }


        if (winner == CellState.CROSS && aiEnabled) {
            winPlayerIA()
        } else if (winner == CellState.CIRCLE && aiEnabled) {
            winIA()
        } else if (winner == CellState.CROSS) {
            winPlayer1()
        } else if (winner == CellState.CIRCLE) {
            winPlayer2()
        }

        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
    }

    private fun onDraw() {
        someoneWon = true

        mensaje("Habéis quedado en tablas")

        if (aiEnabled) {
            drawIA()
        } else {
            draw()
        }

        val resetBoard = Array(9) { CellState.CLEAR }

        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    fun headerConfiguration(player: CellState = CellState.CROSS) {
        val cross = activity.findViewById<AppCompatImageView>(com.example.pmdm_t2_tresenraya.R.id.cross)
        val circle = activity.findViewById<AppCompatImageView>(com.example.pmdm_t2_tresenraya.R.id.circle)
        val player1 = activity.findViewById<TextView>(com.example.pmdm_t2_tresenraya.R.id.player1)
        val player2 = activity.findViewById<TextView>(com.example.pmdm_t2_tresenraya.R.id.player2)

        if (aiEnabled) {
            player1.textSize = 15f
            player2.textSize = 15f
            player2.text = "IA"
        } else {
            if (player == CellState.CROSS) {
                player1.setTypeface(null, Typeface.BOLD)
                player1.textSize = 20f
                player2.setTypeface(null, Typeface.NORMAL)
                player2.textSize = 15f
            } else {
                player2.setTypeface(null, Typeface.BOLD)
                player2.textSize = 20f
                player1.setTypeface(null, Typeface.NORMAL)
                player1.textSize = 15f
            }
        }

    }

    private fun mensaje(message: String) {
        tts.hablar(message, prefs.app.getLanguage())
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun winIA() {
        prefs.game.iap.putGamesPlayed()
        prefs.game.iap.putWinIA()
        prefs.game.iap.modifyPoint(less)
    }
    private fun winPlayerIA() {
        prefs.game.iap.putGamesPlayed()
        prefs.game.iap.putWinPlayer()
        prefs.game.iap.modifyPoint(plus)
    }
    private fun drawIA()  {
        prefs.game.iap.putGamesPlayed()
        prefs.game.iap.putDraws()
        prefs.game.iap.modifyPoint(equals)
    }
    private fun winPlayer1() {
        prefs.game.pvp.putGamesPlayed()
        prefs.game.pvp.putWinPlayer1()
    }
    private fun winPlayer2() {
        prefs.game.pvp.putGamesPlayed()
        prefs.game.pvp.putWinPlayer2()
    }
    private fun draw() {
        prefs.game.pvp.putGamesPlayed()
        prefs.game.pvp.putDraws()
    }
}