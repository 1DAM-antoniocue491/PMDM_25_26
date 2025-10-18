package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import java.util.Arrays

class Game_9_9_Activity : AppCompatActivity() {
    @SuppressLint("DiscouragedApi")
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

        val botones :MutableMap<Int, Int> = mutableMapOf()
        var contador: Int = 1;
        var game: Array<Array<Char>> = arrayOf()
        var piece = true

        for (x in 1..9) {
            var play_row = arrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ')
            game = Arrays.copyOf(game, game.size+1)
            game[game.size-1] = play_row
        }

        for (x in 1..9) {
            for (y in 1..9) {
            var value: String = "celda"
            value += x + y
            val id = resources.getIdentifier(value, "id", packageName)

            botones[contador++] = id
            }
        }


        for (btn_id in 1..81) {
            val id = botones.get(btn_id) ?: 0
            findViewById<Button>(id).setOnClickListener {
                if (piece) {
                    play.setX(findViewById<Button>(id), game, 0, 0)
                    piece = false
                } else {
                    play.setO(findViewById<Button>(id), game, 0, 0)
                    piece = true
                }
            }
        }
    }
}