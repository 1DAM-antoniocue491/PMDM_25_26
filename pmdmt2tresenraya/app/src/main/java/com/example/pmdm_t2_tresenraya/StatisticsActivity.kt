package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.model.Prefs

class StatisticsActivity : AppCompatActivity() {
    private lateinit var prefs: Prefs

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_statistics)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefs = Prefs.getInstance(this)

        updateValues()

        val restart = findViewById<Button>(R.id.btn_restart)
        restart.setOnClickListener {
            prefs.game.smallBoard.restartAll()
            prefs.game.mediumBoard.restartAll()
            prefs.game.bigBoard.restartAll()
            updateValues()
        }
    }

    fun updateValues() {
        findViewById<TextView>(R.id.Igames_played).text = prefs.game.smallBoard.getGamesPlayed().toString()
        findViewById<TextView>(R.id.IwinplayerI).text = prefs.game.smallBoard.getWinPlayer1().toString()
        findViewById<TextView>(R.id.IwinplayerII).text = prefs.game.smallBoard.getWinPlayer2().toString()
        findViewById<TextView>(R.id.Idraws).text = prefs.game.smallBoard.getDraws().toString()

        findViewById<TextView>(R.id.IIgames_played).text = prefs.game.mediumBoard.getGamesPlayed().toString()
        findViewById<TextView>(R.id.IIwinplayerI).text = prefs.game.mediumBoard.getWinPlayer1().toString()
        findViewById<TextView>(R.id.IIwinplayerII).text = prefs.game.mediumBoard.getWinPlayer2().toString()
        findViewById<TextView>(R.id.IIdraws).text = prefs.game.mediumBoard.getDraws().toString()

        findViewById<TextView>(R.id.IIIgames_played).text = prefs.game.bigBoard.getGamesPlayed().toString()
        findViewById<TextView>(R.id.IIIwinplayerI).text = prefs.game.bigBoard.getWinPlayer1().toString()
        findViewById<TextView>(R.id.IIIwinplayerII).text = prefs.game.bigBoard.getWinPlayer2().toString()
        findViewById<TextView>(R.id.IIIdraws).text = prefs.game.bigBoard.getDraws().toString()
    }
}