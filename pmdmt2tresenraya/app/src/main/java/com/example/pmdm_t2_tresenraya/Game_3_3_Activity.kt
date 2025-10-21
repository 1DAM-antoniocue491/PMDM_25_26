package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.model.Play
import java.util.Arrays

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

        val valor = intent.getStringExtra("ia")

        val play = Play(this)

        var game = Array(3) { Array (3) {' '} }
        var piece = true


        for (x in 1..3) {
            for (y in 1..3) {
                var value: String = "celda"
                value += "$x$y"
                val btn_id = resources.getIdentifier(value, "id", packageName)

                val btn: Button = findViewById<Button>(btn_id)
                btn.setOnClickListener {
                    if (valor.equals("true")) {
                        play.setX(btn_id, game, x-1, y-1)
                    } else {
                        if (piece) {
                            play.setX(btn_id, game, x-1, y-1)
                            piece = false
                        } else {
                            play.setO(btn_id, game, x-1, y-1)
                            piece = true
                        }
                    }
                }
            }
        }

    }
}