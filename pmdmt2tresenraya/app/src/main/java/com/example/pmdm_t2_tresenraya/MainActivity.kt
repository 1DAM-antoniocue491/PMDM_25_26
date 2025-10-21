package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val game3 = findViewById<Button>(R.id.game3)

        val typedValue = TypedValue()
        theme.resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValue, true)
        val colorFromTheme = typedValue.data
        game3.setBackgroundColor(colorFromTheme)


        val game6 = findViewById<Button>(R.id.game6)
        val game9 = findViewById<Button>(R.id.game9)

        var boolean_table = arrayOf(true, false, false)

        var tableSize = arrayOf(game3, game6, game9)

        game3.setOnClickListener {
            setButton(game3, tableSize)
            boolean_table = arrayOf(true, false, false)
        }

        game6.setOnClickListener {
            setButton(game6, tableSize)
            boolean_table = arrayOf(false, true, false)
        }

        game9.setOnClickListener {
            setButton(game9, tableSize)
            boolean_table = arrayOf(false, false, true)
        }

        val player1 = findViewById<Button>(R.id.player1)
        val player2 = findViewById<Button>(R.id.player2)

        player1.setBackgroundColor(colorFromTheme)

        var players = arrayOf(player1, player2)

        player1.setOnClickListener {
            setButton(player1, players)
        }

        player2.setOnClickListener {
            setButton(player2, players)
        }

        val computer = findViewById<Button>(R.id.computer)
        val person = findViewById<Button>(R.id.person)

        computer.setBackgroundColor(colorFromTheme)

        var gameMode = arrayOf(computer, person)
        var gameModeBoolean = true

        computer.setOnClickListener {
            setButton(computer, gameMode)
            gameModeBoolean = true
        }

        person.setOnClickListener {
            setButton(person, gameMode)
            gameModeBoolean = false
        }

        val play = findViewById<Button>(R.id.play)

        play.setOnClickListener {
            var intent: Intent? = null


            if (boolean_table[0]) {
                intent = Intent(this, Game_3_3_Activity::class.java)
                if (gameModeBoolean) {
                    intent.putExtra("ia", "true")
                } else {
                    intent.putExtra("ia", "false")
                }
            } else if (boolean_table[1]) {
                intent = Intent(this, Game_6_6_Activity::class.java)
                if (gameModeBoolean) {
                    intent.putExtra("ia", "true")
                } else {
                    intent.putExtra("ia", "false")
                }
            } else {
                intent = Intent(this, Game_9_9_Activity::class.java)
                if (gameModeBoolean) {
                    intent.putExtra("ia", "true")
                } else {
                    intent.putExtra("ia", "false")
                }
            }

            startActivity(intent)
        }
    }

    fun setButton(btn_add: Button, prueba: Array<Button>) {
        val typedValueSecondary = TypedValue()
        val typeValueOnPrimary = TypedValue()

        theme.resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValueSecondary, true)
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typeValueOnPrimary, true)

        val colorSecondary = typedValueSecondary.data
        val colorOnPrimary = typeValueOnPrimary.data

        for (btn in prueba) {
            if (btn == btn_add) {
                btn.setBackgroundColor(colorSecondary)
            } else {
                btn.setBackgroundColor(colorOnPrimary)
            }
        }
    }
}