package com.example.pmdm_t2_tresenraya.controller

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.R
import com.example.pmdm_t2_tresenraya.model.Play
import com.example.pmdm_t2_tresenraya.model.Prefs
import com.example.pmdm_t2_tresenraya.model.TTS

class StatisticsActivity : AppCompatActivity() {
    private lateinit var prefs: Prefs
    private val tts: TTS = TTS.getInstance(this)

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
        val play = Play(this)

        updateValues()

        val restart = findViewById<Button>(R.id.btn_restart)

        play.styleButton(restart,this)

        restart.setOnClickListener { restartAll() }
    }

    fun updateValues() {
        findViewById<TextView>(R.id.game_PVP).text = prefs.game.pvp.getGamesPlayed().toString()
        findViewById<TextView>(R.id.win1_PVP).text = prefs.game.pvp.getWinPlayer1().toString()
        findViewById<TextView>(R.id.win2_PVP).text = prefs.game.pvp.getWinPlayer2().toString()
        findViewById<TextView>(R.id.draws_PVP).text = prefs.game.pvp.getDraws().toString()

        findViewById<TextView>(R.id.game_IAP).text = prefs.game.iap.getGamesPlayed().toString()
        findViewById<TextView>(R.id.win1_IAP).text = prefs.game.iap.getWinPlayer().toString()
        findViewById<TextView>(R.id.win2_IAP).text = prefs.game.iap.getWinIA().toString()
        findViewById<TextView>(R.id.draws_IAP).text = prefs.game.iap.getDraws().toString()
    }

    fun restartAll() {
        prefs.game.pvp.restartAll()
        prefs.game.iap.restartAll()
        prefs.app.restartLevels()
        updateValues()
        tts.hablar("Los niveles y las estadísticas se han reiniciado correctamente", prefs.app.getLanguage())
    }
}