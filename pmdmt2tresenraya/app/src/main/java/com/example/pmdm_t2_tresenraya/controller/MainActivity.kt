package com.example.pmdm_t2_tresenraya.controller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.R
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

    private var nivelAnterior: Int = 0


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
        playClass = Play(this)
        tts = TTS.getInstance(this)

        initConfiguration()

        players()

        contrincante()

        navigationIcons()

        checkLevel()

        val play = findViewById<Button>(R.id.play)
        playClass.styleButton(play, this)

        play.setOnClickListener { playBtn() }
    }

    fun initConfiguration() {
        if (nivelAnterior == 0) {
            nivelAnterior = prefs.game.iap.getPreviousLevel()
        }

        if (prefs.app.isDarkMode(this)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        Sound.setVolume(prefs.app.getVolumeSound())
        Sound.playBackground(this, prefs.app.getBackgroundSound())

        val typedValueSecondary = TypedValue()
        val typeValueOnPrimary = TypedValue()

        theme.resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValueSecondary, true)
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typeValueOnPrimary, true)

        colorSecondary = typedValueSecondary.data
        colorOnPrimary = typeValueOnPrimary.data
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
            prefs.app.putStart(true)
        } else {
            player1.setBackgroundColor(colorOnPrimary)
            player2.setBackgroundColor(colorOnPrimary)
        }

        player1.setOnClickListener {
            if (prefs.app.getGameMode() == "false") {
                setButton(player1, players)
                prefs.app.putStart(true)
            }
        }

        player2.setOnClickListener {
            if (prefs.app.getGameMode() == "false") {
                setButton(player2, players)
                prefs.app.putStart(false)
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

    fun navigationIcons() {
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
    }

    fun checkLevel() {
        val nivelActual = prefs.game.iap.getPoints().div(4)

        if (nivelActual > nivelAnterior) {
            mostrarAnimacionSubidaNivel(findViewById(R.id.main), nivelActual)
            prefs.game.iap.putPreviousLevel(nivelActual)
        }
    }

    fun mostrarAnimacionSubidaNivel(view: View, nivel: Int) {
        val messageView = view.findViewById<TextView>(R.id.levelUpMessage)

        // Texto del mensaje
        messageView.text = "¡Nivel $nivel alcanzado!"
        messageView.visibility = View.VISIBLE

        // Punto de partida: fuera de la pantalla (arriba)
        messageView.alpha = 0f
        messageView.translationY = -500f // empieza más arriba
        messageView.scaleX = 0.8f
        messageView.scaleY = 0.8f

        // Primera animación: baja con rebote
        messageView.animate()
            .translationY(0f)               // posición original
            .alpha(1f)                      // aparece
            .scaleX(1.1f)                   // crece un poco
            .scaleY(1.1f)
            .setInterpolator(android.view.animation.BounceInterpolator())
            .setDuration(2000)
            .withEndAction {
                // Pausa visible un momento
                messageView.postDelayed({
                    // Segunda animación: desaparecer hacia abajo
                    messageView.animate()
                        .translationY(400f)  // se va hacia abajo
                        .alpha(0f)
                        .setDuration(1000)
                        .setInterpolator(android.view.animation.AccelerateInterpolator())
                        .withEndAction {
                            // Ocultar finalmente
                            messageView.visibility = View.GONE
                            messageView.translationY = 0f
                        }
                }, 1200) // tiempo visible antes de desaparecer
            }
    }

    fun playBtn() {
        tts.setVoz(prefs.app.getTTS())
        tts.hablar("Empieza la partida", prefs.app.getLanguage())
        Thread.sleep(1000)
        var intent = Intent(this, Game_3_3_Activity::class.java)
        startActivity(intent)
        val player1 = findViewById<Button>(R.id.player1)
        val player2 = findViewById<Button>(R.id.player2)
        player1.setBackgroundColor(colorOnPrimary)
        player2.setBackgroundColor(colorOnPrimary)
    }
}