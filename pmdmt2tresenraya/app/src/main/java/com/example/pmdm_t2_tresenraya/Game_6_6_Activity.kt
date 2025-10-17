package com.example.pmdm_t2_tresenraya

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Game_6_6_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game66)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val play = Play(this)

        val btn1 = findViewById<Button>(R.id.celda11)
        val btn2 = findViewById<Button>(R.id.celda12)
        val btn3 = findViewById<Button>(R.id.celda13)
        val btn4 = findViewById<Button>(R.id.celda14)
        val btn5 = findViewById<Button>(R.id.celda15)
        val btn6 = findViewById<Button>(R.id.celda16)

        val btn7 = findViewById<Button>(R.id.celda21)
        val btn8 = findViewById<Button>(R.id.celda22)
        val btn9 = findViewById<Button>(R.id.celda23)
        val btn10 = findViewById<Button>(R.id.celda24)
        val btn11 = findViewById<Button>(R.id.celda25)
        val btn12 = findViewById<Button>(R.id.celda26)

        val btn13 = findViewById<Button>(R.id.celda31)
        val btn14 = findViewById<Button>(R.id.celda32)
        val btn15 = findViewById<Button>(R.id.celda33)
        val btn16 = findViewById<Button>(R.id.celda34)
        val btn17 = findViewById<Button>(R.id.celda35)
        val btn18 = findViewById<Button>(R.id.celda36)

        val btn19 = findViewById<Button>(R.id.celda41)
        val btn20 = findViewById<Button>(R.id.celda42)
        val btn21 = findViewById<Button>(R.id.celda43)
        val btn22 = findViewById<Button>(R.id.celda44)
        val btn23 = findViewById<Button>(R.id.celda45)
        val btn24 = findViewById<Button>(R.id.celda46)

        val btn25 = findViewById<Button>(R.id.celda51)
        val btn26 = findViewById<Button>(R.id.celda52)
        val btn27 = findViewById<Button>(R.id.celda53)
        val btn28 = findViewById<Button>(R.id.celda54)
        val btn29 = findViewById<Button>(R.id.celda55)
        val btn30 = findViewById<Button>(R.id.celda56)

        val btn31 = findViewById<Button>(R.id.celda61)
        val btn32 = findViewById<Button>(R.id.celda62)
        val btn33 = findViewById<Button>(R.id.celda63)
        val btn34 = findViewById<Button>(R.id.celda64)
        val btn35 = findViewById<Button>(R.id.celda65)
        val btn36 = findViewById<Button>(R.id.celda66)

        val row1 = arrayOf(btn1, btn2, btn3, btn4, btn5, btn6)
        val row2 = arrayOf(btn7, btn8, btn9, btn10, btn11, btn12)
        val row3 = arrayOf(btn13, btn14, btn15, btn16, btn17, btn18)
        val row4 = arrayOf(btn19, btn20, btn21, btn22, btn23, btn24)
        val row5 = arrayOf(btn25, btn26, btn27, btn28, btn29, btn30)
        val row6 = arrayOf(btn31, btn32, btn33, btn34, btn35, btn36)

        val table = arrayOf(row1, row2, row3, row4, row5, row6)

        play.setBaseColor(table)

        var play_row1 = arrayOf(' ', ' ', ' ', ' ', ' ', ' ')
        var play_row2 = arrayOf(' ', ' ', ' ', ' ', ' ', ' ')
        var play_row3 = arrayOf(' ', ' ', ' ', ' ', ' ', ' ')
        var play_row4 = arrayOf(' ', ' ', ' ', ' ', ' ', ' ')
        var play_row5 = arrayOf(' ', ' ', ' ', ' ', ' ', ' ')
        var play_row6 = arrayOf(' ', ' ', ' ', ' ', ' ', ' ')
        var game = arrayOf(play_row1, play_row2, play_row3, play_row4, play_row5, play_row6)

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
                play.setX(btn4, game, 0, 3)
                piece = false
            } else {
                play.setO(btn4, game, 0, 3)
                piece = true
            }
        }

        btn5.setOnClickListener {
            if (piece) {
                play.setX(btn5, game, 0, 4)
                piece = false
            } else {
                play.setO(btn5, game, 0, 4)
                piece = true
            }
        }

        btn6.setOnClickListener {
            if (piece) {
                play.setX(btn6, game, 0, 5)
                piece = false
            } else {
                play.setO(btn6, game, 0, 5)
                piece = true
            }
        }

        btn7.setOnClickListener {
            if (piece) {
                play.setX(btn7, game, 1, 0)
                piece = false
            } else {
                play.setO(btn7, game, 1, 0)
                piece = true
            }
        }

        btn8.setOnClickListener {
            if (piece) {
                play.setX(btn8, game, 1, 1)
                piece = false
            } else {
                play.setO(btn8, game, 1, 1)
                piece = true
            }
        }

        btn9.setOnClickListener {
            if (piece) {
                play.setX(btn9, game, 1, 2)
                piece = false
            } else {
                play.setO(btn9, game, 1, 2)
                piece = true
            }
        }

        btn10.setOnClickListener {
            if (piece) {
                play.setX(btn10, game, 1, 3)
                piece = false
            } else {
                play.setO(btn10, game, 1, 3)
                piece = true
            }
        }

        btn11.setOnClickListener {
            if (piece) {
                play.setX(btn11, game, 1, 4)
                piece = false
            } else {
                play.setO(btn11, game, 1, 4)
                piece = true
            }
        }

        btn12.setOnClickListener {
            if (piece) {
                play.setX(btn12, game, 1, 5)
                piece = false
            } else {
                play.setO(btn12, game, 1, 5)
                piece = true
            }
        }

        btn13.setOnClickListener {
            if (piece) {
                play.setX(btn13, game, 2, 0)
                piece = false
            } else {
                play.setO(btn13, game, 2, 0)
                piece = true
            }
        }

        btn14.setOnClickListener {
            if (piece) {
                play.setX(btn14, game, 2, 1)
                piece = false
            } else {
                play.setO(btn14, game, 2, 1)
                piece = true
            }
        }

        btn15.setOnClickListener {
            if (piece) {
                play.setX(btn15, game, 2, 2)
                piece = false
            } else {
                play.setO(btn15, game, 2, 2)
                piece = true
            }
        }

        btn16.setOnClickListener {
            if (piece) {
                play.setX(btn16, game, 2, 3)
                piece = false
            } else {
                play.setO(btn16, game, 2, 3)
                piece = true
            }
        }

        btn17.setOnClickListener {
            if (piece) {
                play.setX(btn17, game, 2, 4)
                piece = false
            } else {
                play.setO(btn17, game, 2, 4)
                piece = true
            }
        }

        btn18.setOnClickListener {
            if (piece) {
                play.setX(btn18, game, 2, 5)
                piece = false
            } else {
                play.setO(btn18, game, 2, 5)
                piece = true
            }
        }

        btn19.setOnClickListener {
            if (piece) {
                play.setX(btn19, game, 3, 0)
                piece = false
            } else {
                play.setO(btn19, game, 3, 0)
                piece = true
            }
        }

        btn20.setOnClickListener {
            if (piece) {
                play.setX(btn20, game, 3, 1)
                piece = false
            } else {
                play.setO(btn20, game, 3, 1)
                piece = true
            }
        }

        btn21.setOnClickListener {
            if (piece) {
                play.setX(btn21, game, 3, 2)
                piece = false
            } else {
                play.setO(btn21, game, 3, 2)
                piece = true
            }
        }

        btn22.setOnClickListener {
            if (piece) {
                play.setX(btn22, game, 3, 3)
                piece = false
            } else {
                play.setO(btn22, game, 3, 3)
                piece = true
            }
        }

        btn23.setOnClickListener {
            if (piece) {
                play.setX(btn23, game, 3, 4)
                piece = false
            } else {
                play.setO(btn23, game, 3, 4)
                piece = true
            }
        }

        btn24.setOnClickListener {
            if (piece) {
                play.setX(btn24, game, 3, 5)
                piece = false
            } else {
                play.setO(btn24, game, 3, 5)
                piece = true
            }
        }

        btn25.setOnClickListener {
            if (piece) {
                play.setX(btn25, game, 4, 0)
                piece = false
            } else {
                play.setO(btn25, game, 4, 0)
                piece = true
            }
        }

        btn26.setOnClickListener {
            if (piece) {
                play.setX(btn26, game, 4, 1)
                piece = false
            } else {
                play.setO(btn26, game, 4, 1)
                piece = true
            }
        }

        btn27.setOnClickListener {
            if (piece) {
                play.setX(btn27, game, 4, 2)
                piece = false
            } else {
                play.setO(btn27, game, 4, 2)
                piece = true
            }
        }

        btn28.setOnClickListener {
            if (piece) {
                play.setX(btn28, game, 4, 3)
                piece = false
            } else {
                play.setO(btn28, game, 4, 3)
                piece = true
            }
        }

        btn29.setOnClickListener {
            if (piece) {
                play.setX(btn29, game, 4, 4)
                piece = false
            } else {
                play.setO(btn29, game, 4, 4)
                piece = true
            }
        }

        btn30.setOnClickListener {
            if (piece) {
                play.setX(btn30, game, 4, 5)
                piece = false
            } else {
                play.setO(btn30, game, 4, 5)
                piece = true
            }
        }

        btn31.setOnClickListener {
            if (piece) {
                play.setX(btn31, game, 5, 0)
                piece = false
            } else {
                play.setO(btn31, game, 5, 0)
                piece = true
            }
        }

        btn32.setOnClickListener {
            if (piece) {
                play.setX(btn32, game, 5, 1)
                piece = false
            } else {
                play.setO(btn32, game, 5, 1)
                piece = true
            }
        }

        btn33.setOnClickListener {
            if (piece) {
                play.setX(btn33, game, 5, 2)
                piece = false
            } else {
                play.setO(btn33, game, 5, 2)
                piece = true
            }
        }

        btn34.setOnClickListener {
            if (piece) {
                play.setX(btn34, game, 5, 3)
                piece = false
            } else {
                play.setO(btn34, game, 5, 3)
                piece = true
            }
        }

        btn35.setOnClickListener {
            if (piece) {
                play.setX(btn35, game, 5, 4)
                piece = false
            } else {
                play.setO(btn35, game, 5, 4)
                piece = true
            }
        }

        btn36.setOnClickListener {
            if (piece) {
                play.setX(btn36, game, 5, 5)
                piece = false
            } else {
                play.setO(btn36, game, 5, 5)
                piece = true
            }
        }
    }
}