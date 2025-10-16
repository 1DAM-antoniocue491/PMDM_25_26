package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Game_3_3_Activity : AppCompatActivity() {
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

        val btn1 = findViewById<Button>(R.id.celda11)
        val btn2 = findViewById<Button>(R.id.celda12)
        val btn3 = findViewById<Button>(R.id.celda13)
        val btn4 = findViewById<Button>(R.id.celda21)
        val btn5 = findViewById<Button>(R.id.celda22)
        val btn6 = findViewById<Button>(R.id.celda23)
        val btn7 = findViewById<Button>(R.id.celda31)
        val btn8 = findViewById<Button>(R.id.celda32)
        val btn9 = findViewById<Button>(R.id.celda33)

        var row1 = arrayOf(btn1, btn2, btn3)
        var row2 = arrayOf(btn4, btn5, btn6)
        var row3 = arrayOf(btn7, btn8, btn9)

        var table = arrayOf(row1, row2, row3)

        val play = Play(this)
        play.setBaseColor(table)

        var play_row1 = arrayOf(' ',' ',' ')
        var play_row2 = arrayOf(' ',' ',' ')
        var play_row3 = arrayOf(' ',' ',' ')
        var game = arrayOf(play_row1, play_row2, play_row3)

        var piece = true


        btn1.setOnClickListener {
            if (piece) {
                play.setX(btn1, game, 0, 0)
                piece = false
            } else {
                play.setO(btn1, game, 0, 0)
                piece = true
            }
        }

        btn2.setOnClickListener {
            if (piece) {
                play.setX(btn2, game, 0, 1)
                piece = false
            } else {
                play.setO(btn2, game, 0, 1)
                piece = true
            }
        }

        btn3.setOnClickListener {
            if (piece) {
                play.setX(btn3, game, 0, 2)
                piece = false
            } else {
                play.setO(btn3, game, 0, 2)
                piece = true
            }
        }

        btn4.setOnClickListener {
            if (piece) {
                play.setX(btn4, game, 1, 0)
                piece = false
            } else {
                play.setO(btn4, game, 1, 0)
                piece = true
            }
        }

        btn5.setOnClickListener {
            if (piece) {
                play.setX(btn5, game, 1, 1)
                piece = false
            } else {
                play.setO(btn5, game, 1, 1)
                piece = true
            }
        }

        btn6.setOnClickListener {
            if (piece) {
                play.setX(btn6, game, 1, 2)
                piece = false
            } else {
                play.setO(btn6, game, 1, 2)
                piece = true
            }
        }

        btn7.setOnClickListener {
            if (piece) {
                play.setX(btn7, game, 2, 0)
                piece = false
            } else {
                play.setO(btn7, game, 2, 0)
                piece = true
            }
        }

        btn8.setOnClickListener {
            if (piece) {
                play.setX(btn8, game, 2, 1)
                piece = false
            } else {
                play.setO(btn8, game, 2, 1)
                piece = true
            }
        }

        btn9.setOnClickListener {
            if (piece) {
                play.setX(btn9, game, 2, 2)
                piece = false
            } else {
                play.setO(btn9, game, 2, 2)
                piece = true
            }
        }
    }
}