package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.model.CellState
import com.example.pmdm_t2_tresenraya.model.IA
import com.example.pmdm_t2_tresenraya.model.IA_plus
import com.example.pmdm_t2_tresenraya.model.Play

class Game_3_3_Activity : AppCompatActivity() {

    private lateinit var juego: Array<Array<CellState>>
    private var isXTurn: Boolean = true
    private lateinit var play: Play
    private lateinit var ia: IA
    private var aiEnabled: Boolean = false
    private var someOneWin: Boolean = false

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
        juego = Array(3) { Array(3) { CellState.CLEAR } }

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
                    if (!someOneWin) {
                        onPlayerMove(row, col, btn)
                    }
                }
            }
        }
    }

    private fun onPlayerMove(row: Int, col: Int, btn: Button) {
        if (juego[row][col] != CellState.CLEAR) return // Casilla ocupada

        Log.i("Prueba", "BTN ID: ${row+1} : ${col+1}")
        if (isXTurn) {
            play.setX(btn.id, juego, row, col)
            juego[row][col] = CellState.CROSS
        } else {
            play.setO(btn.id, juego, row, col)
            juego[row][col] = CellState.CIRCLE
        }

        Log.v("Prueba", "game content: " + gameContent())

        // Revisar si alguien ganó
        if (ia.checkWin(juego)) {
            Log.v("Prueba", "¡Ganó ${if (isXTurn) "X" else "O"}!")
            someOneWin = true
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "¡Ganó ${if (isXTurn) "X" else "O"}!", Toast.LENGTH_SHORT).show()
            return
        } else {
            // Tablas
            var i: Int = 0
            for (row in 0..2) {
                for (col in 0..2) {
                    if (juego[row][col] != CellState.CLEAR)
                        i++
                }
            }
            if (i == 9) {
                Log.v("Prueba", "¡Tablas!")
                someOneWin = true
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "¡Tablas!", Toast.LENGTH_SHORT).show()
                return
            }
        }

        isXTurn = !isXTurn

        // IA mueve si está activada y es su turno
        if (aiEnabled && !isXTurn) {
            val aiPos = ia.bestPosition(juego)
            iaTurn(aiPos)
        }

        if (someOneWin)  {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun iaTurn(pos: Pair<Int, Int>) {
        val row = pos.first
        val col = pos.second
        val btnIdName = "celda${row + 1}${col + 1}"
        val btnId = resources.getIdentifier(btnIdName, "id", packageName)
        val btn: Button? = findViewById(btnId)

        btn?.let {
            play.setO(btn.id, juego, row, col)
            juego[row][col] = CellState.CIRCLE
            Log.v("Prueba", "IA movió:")
            Log.v("Prueba", gameContent())
        }

        // Revisar si IA ganó
        if (ia.checkWin(juego)) {
            Log.v("Prueba", "¡Ganó la IA!")
            Toast.makeText(this, "¡Ganó la IA!", Toast.LENGTH_SHORT).show()
            someOneWin = true
            return
        } else {
            // Tablas
            var i: Int = 0
            for (row in 0..2) {
                for (col in 0..2) {
                    if (juego[row][col] != CellState.CLEAR)
                        i++
                }
            }
            if (i == 9) {
                Log.v("Prueba", "¡Tablas!")
                someOneWin = true
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "¡Tablas!", Toast.LENGTH_SHORT).show()
                return
            }
        }



        isXTurn = true
    }

    private fun gameContent(): String {
        return juego.joinToString("\n") { it.joinToString(" ") }
    }
}
