package com.example.pmdm_t2_tresenraya

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class Game_9_9_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game99)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val  play = Play(this)

        // Referencias a los 81 botones
        val btn11 = findViewById<Button>(R.id.celda11)
        val btn12 = findViewById<Button>(R.id.celda12)
        val btn13 = findViewById<Button>(R.id.celda13)
        val btn14 = findViewById<Button>(R.id.celda14)
        val btn15 = findViewById<Button>(R.id.celda15)
        val btn16 = findViewById<Button>(R.id.celda16)
        val btn17 = findViewById<Button>(R.id.celda17)
        val btn18 = findViewById<Button>(R.id.celda18)
        val btn19 = findViewById<Button>(R.id.celda19)

        val btn21 = findViewById<Button>(R.id.celda21)
        val btn22 = findViewById<Button>(R.id.celda22)
        val btn23 = findViewById<Button>(R.id.celda23)
        val btn24 = findViewById<Button>(R.id.celda24)
        val btn25 = findViewById<Button>(R.id.celda25)
        val btn26 = findViewById<Button>(R.id.celda26)
        val btn27 = findViewById<Button>(R.id.celda27)
        val btn28 = findViewById<Button>(R.id.celda28)
        val btn29 = findViewById<Button>(R.id.celda29)

        val btn31 = findViewById<Button>(R.id.celda31)
        val btn32 = findViewById<Button>(R.id.celda32)
        val btn33 = findViewById<Button>(R.id.celda33)
        val btn34 = findViewById<Button>(R.id.celda34)
        val btn35 = findViewById<Button>(R.id.celda35)
        val btn36 = findViewById<Button>(R.id.celda36)
        val btn37 = findViewById<Button>(R.id.celda37)
        val btn38 = findViewById<Button>(R.id.celda38)
        val btn39 = findViewById<Button>(R.id.celda39)

        val btn41 = findViewById<Button>(R.id.celda41)
        val btn42 = findViewById<Button>(R.id.celda42)
        val btn43 = findViewById<Button>(R.id.celda43)
        val btn44 = findViewById<Button>(R.id.celda44)
        val btn45 = findViewById<Button>(R.id.celda45)
        val btn46 = findViewById<Button>(R.id.celda46)
        val btn47 = findViewById<Button>(R.id.celda47)
        val btn48 = findViewById<Button>(R.id.celda48)
        val btn49 = findViewById<Button>(R.id.celda49)

        val btn51 = findViewById<Button>(R.id.celda51)
        val btn52 = findViewById<Button>(R.id.celda52)
        val btn53 = findViewById<Button>(R.id.celda53)
        val btn54 = findViewById<Button>(R.id.celda54)
        val btn55 = findViewById<Button>(R.id.celda55)
        val btn56 = findViewById<Button>(R.id.celda56)
        val btn57 = findViewById<Button>(R.id.celda57)
        val btn58 = findViewById<Button>(R.id.celda58)
        val btn59 = findViewById<Button>(R.id.celda59)

        val btn61 = findViewById<Button>(R.id.celda61)
        val btn62 = findViewById<Button>(R.id.celda62)
        val btn63 = findViewById<Button>(R.id.celda63)
        val btn64 = findViewById<Button>(R.id.celda64)
        val btn65 = findViewById<Button>(R.id.celda65)
        val btn66 = findViewById<Button>(R.id.celda66)
        val btn67 = findViewById<Button>(R.id.celda67)
        val btn68 = findViewById<Button>(R.id.celda68)
        val btn69 = findViewById<Button>(R.id.celda69)

        val btn71 = findViewById<Button>(R.id.celda71)
        val btn72 = findViewById<Button>(R.id.celda72)
        val btn73 = findViewById<Button>(R.id.celda73)
        val btn74 = findViewById<Button>(R.id.celda74)
        val btn75 = findViewById<Button>(R.id.celda75)
        val btn76 = findViewById<Button>(R.id.celda76)
        val btn77 = findViewById<Button>(R.id.celda77)
        val btn78 = findViewById<Button>(R.id.celda78)
        val btn79 = findViewById<Button>(R.id.celda79)

        val btn81 = findViewById<Button>(R.id.celda81)
        val btn82 = findViewById<Button>(R.id.celda82)
        val btn83 = findViewById<Button>(R.id.celda83)
        val btn84 = findViewById<Button>(R.id.celda84)
        val btn85 = findViewById<Button>(R.id.celda85)
        val btn86 = findViewById<Button>(R.id.celda86)
        val btn87 = findViewById<Button>(R.id.celda87)
        val btn88 = findViewById<Button>(R.id.celda88)
        val btn89 = findViewById<Button>(R.id.celda89)

        val btn91 = findViewById<Button>(R.id.celda91)
        val btn92 = findViewById<Button>(R.id.celda92)
        val btn93 = findViewById<Button>(R.id.celda93)
        val btn94 = findViewById<Button>(R.id.celda94)
        val btn95 = findViewById<Button>(R.id.celda95)
        val btn96 = findViewById<Button>(R.id.celda96)
        val btn97 = findViewById<Button>(R.id.celda97)
        val btn98 = findViewById<Button>(R.id.celda98)
        val btn99 = findViewById<Button>(R.id.celda99)

// Filas
        val row1 = arrayOf(btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19)
        val row2 = arrayOf(btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28, btn29)
        val row3 = arrayOf(btn31, btn32, btn33, btn34, btn35, btn36, btn37, btn38, btn39)
        val row4 = arrayOf(btn41, btn42, btn43, btn44, btn45, btn46, btn47, btn48, btn49)
        val row5 = arrayOf(btn51, btn52, btn53, btn54, btn55, btn56, btn57, btn58, btn59)
        val row6 = arrayOf(btn61, btn62, btn63, btn64, btn65, btn66, btn67, btn68, btn69)
        val row7 = arrayOf(btn71, btn72, btn73, btn74, btn75, btn76, btn77, btn78, btn79)
        val row8 = arrayOf(btn81, btn82, btn83, btn84, btn85, btn86, btn87, btn88, btn89)
        val row9 = arrayOf(btn91, btn92, btn93, btn94, btn95, btn96, btn97, btn98, btn99)

// Tabla completa
        val table = arrayOf(row1, row2, row3, row4, row5, row6, row7, row8, row9)

// Estado del juego
        val play_row1 = arrayOf(' ',' ',' ',' ',' ',' ',' ',' ',' ')
        val play_row2 = arrayOf(' ',' ',' ',' ',' ',' ',' ',' ',' ')
        val play_row3 = arrayOf(' ',' ',' ',' ',' ',' ',' ',' ',' ')
        val play_row4 = arrayOf(' ',' ',' ',' ',' ',' ',' ',' ',' ')
        val play_row5 = arrayOf(' ',' ',' ',' ',' ',' ',' ',' ',' ')
        val play_row6 = arrayOf(' ',' ',' ',' ',' ',' ',' ',' ',' ')
        val play_row7 = arrayOf(' ',' ',' ',' ',' ',' ',' ',' ',' ')
        val play_row8 = arrayOf(' ',' ',' ',' ',' ',' ',' ',' ',' ')
        val play_row9 = arrayOf(' ',' ',' ',' ',' ',' ',' ',' ',' ')

        val game = arrayOf(play_row1, play_row2, play_row3, play_row4, play_row5, play_row6, play_row7, play_row8, play_row9)

        var piece = true

// Listeners (manuales)
        val botones = listOf(
            btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19,
            btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28, btn29,
            btn31, btn32, btn33, btn34, btn35, btn36, btn37, btn38, btn39,
            btn41, btn42, btn43, btn44, btn45, btn46, btn47, btn48, btn49,
            btn51, btn52, btn53, btn54, btn55, btn56, btn57, btn58, btn59,
            btn61, btn62, btn63, btn64, btn65, btn66, btn67, btn68, btn69,
            btn71, btn72, btn73, btn74, btn75, btn76, btn77, btn78, btn79,
            btn81, btn82, btn83, btn84, btn85, btn86, btn87, btn88, btn89,
            btn91, btn92, btn93, btn94, btn95, btn96, btn97, btn98, btn99
        )

// Asigna listeners con coordenadas manuales
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                val btn = table[i][j]
                btn.setOnClickListener {
                    if (piece) {
                        play.setX(btn, game, i, j)
                        piece = false
                    } else {
                        play.setO(btn, game, i, j)
                        piece = true
                    }
                }
            }
        }

    }
}