package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.model.Play
import com.example.pmdm_t2_tresenraya.model.Prefs
import com.example.pmdm_t2_tresenraya.model.Sound
import com.example.pmdm_t2_tresenraya.model.TTS

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: Prefs
    private var colorSecondary: Int = 0
    private var colorOnPrimary: Int = 0
    private lateinit var playClass: Play
    private lateinit var tts: TTS


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

        playClass = Play(this)

        tts = TTS.getInstance(this)
        prefs = Prefs.getInstance(this)

        if (prefs.app.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        Sound.setVolume(prefs.app.getVolumeSound())
        Sound.playBackground(this, prefs.app.getBackgroundSound())

        val settings = findViewById<ImageButton>(R.id.settings)
        settings.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        val statistics = findViewById<ImageButton>(R.id.statistics)
        statistics.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }

        val levels = findViewById<ImageButton>(R.id.levels)
        levels.setOnClickListener {
            val intent = Intent(this, LevelsActivity::class.java)
            startActivity(intent)
        }

        val typedValueSecondary = TypedValue()
        val typeValueOnPrimary = TypedValue()

        theme.resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValueSecondary, true)
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typeValueOnPrimary, true)

        colorSecondary = typedValueSecondary.data
        colorOnPrimary = typeValueOnPrimary.data

        players()

        contrincante()

        val play = findViewById<Button>(R.id.play)

        playClass.styleButton(play, this)

        play.setOnClickListener {
            tts.setVoz(prefs.app.getTTS())
            tts.hablar("Empieza la partida")
            Thread.sleep(1000)
            var intent = Intent(this, Game_3_3_Activity::class.java)
            startActivity(intent)
            val player1 = findViewById<Button>(R.id.player1)
            val player2 = findViewById<Button>(R.id.player2)
            player1.setBackgroundColor(colorOnPrimary)
            player2.setBackgroundColor(colorOnPrimary)

        }
    }

    fun setButton(btn_add: Button, buttons: Array<Button>) {
        for (btn in buttons) {
            if (btn == btn_add) {
                playClass.styleButton(btn, this)
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
            playClass.styleButton(player1, this)
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

    fun contrincante(){
        val computer = findViewById<Button>(R.id.computer)
        val person = findViewById<Button>(R.id.person)

        if (prefs.app.getGameMode() == "true") {
            playClass.styleButton(computer, this)
            prefs.app.putGameMode("true")
        } else {
            playClass.styleButton(person, this)
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

    override fun onDestroy() {
        super.onDestroy()
        TTS.destroy()
        Sound.stopBackground()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}