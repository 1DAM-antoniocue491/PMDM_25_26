package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.model.Prefs

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: Prefs
    private var colorSecondary: Int = 0
    private var colorOnPrimary: Int = 0


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

        prefs = Prefs.getInstance(this)

        val settings = findViewById<ImageButton>(R.id.settings)
        settings.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        val account = findViewById<ImageButton>(R.id.acount)
        account.setOnClickListener {
            showCustomDialog()
        }

        val typedValueSecondary = TypedValue()
        val typeValueOnPrimary = TypedValue()

        theme.resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValueSecondary, true)
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typeValueOnPrimary, true)

        colorSecondary = typedValueSecondary.data
        colorOnPrimary = typeValueOnPrimary.data

        players()

        contrincante(colorSecondary)

        val play = findViewById<Button>(R.id.play)

        play.setOnClickListener {
            var intent = Intent(this, Game_3_3_Activity::class.java)
            startActivity(intent)
            val player1 = findViewById<Button>(R.id.player1)
            val player2 = findViewById<Button>(R.id.player2)
            player1.setBackgroundColor(colorOnPrimary)
            player2.setBackgroundColor(colorOnPrimary)
        }
    }

    fun setButton(btn_add: Button, prueba: Array<Button>) {
        for (btn in prueba) {
            if (btn == btn_add) {
                btn.setBackgroundColor(colorSecondary)
            } else {
                btn.setBackgroundColor(colorOnPrimary)
            }
        }
    }

    fun players () {
        val player1 = findViewById<Button>(R.id.player1)
        val player2 = findViewById<Button>(R.id.player2)

        val players = arrayOf(player1, player2)

        if (prefs.app.getGameMode() == "false") {
            player1.setBackgroundColor(colorSecondary)
            prefs.app.putStart("player1")
        } else {
            player1.setBackgroundColor(colorOnPrimary)
            player2.setBackgroundColor(colorOnPrimary)
        }

        player1.setOnClickListener {
            if (prefs.app.getGameMode() == "false") {
                setButton(player1, players)
                prefs.app.putStart("player1")
            }
        }

        player2.setOnClickListener {
            if (prefs.app.getGameMode() == "false") {
                setButton(player2, players)
                prefs.app.putStart("player2")
            }
        }
    }

    fun contrincante(colorFromTheme: Int){
        val computer = findViewById<Button>(R.id.computer)
        val person = findViewById<Button>(R.id.person)

        if (prefs.app.getGameMode() == "true") {
            computer.setBackgroundColor(colorFromTheme)
            prefs.app.putGameMode("true")
        } else {
            person.setBackgroundColor(colorFromTheme)
            prefs.app.putGameMode("false")
        }

        var gameMode = arrayOf(computer, person)

        computer.setOnClickListener {
            setButton(computer, gameMode)
            prefs.app.putGameMode("true")
            players()
        }

        person.setOnClickListener {
            setButton(person, gameMode)
            prefs.app.putGameMode("false")
            players()
        }
    }

    @SuppressLint("MissingInflatedId")
    fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_acount, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Para que el fondo fuera del diálogo sea semitransparente
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Referencias a los botones
        val statistics = dialogView.findViewById<Button>(R.id.statistics)

        statistics.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }

        val levels = dialogView.findViewById<Button>(R.id.levels)

        levels.setOnClickListener {
            val intent = Intent(this, LevelActivity::class.java)
            startActivity(intent)
        }


        dialog.show()
    }

}