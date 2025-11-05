package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.model.Play
import com.example.pmdm_t2_tresenraya.model.Prefs
import com.example.pmdm_t2_tresenraya.model.Sound
import com.example.pmdm_t2_tresenraya.model.TTS
import java.util.Locale

class SettingActivity : AppCompatActivity() {
    private lateinit var prefs: Prefs
    private lateinit var tts: TTS
    private var color: String = ""
    val play = Play(this)
    private lateinit var defaultTheme: String
    private lateinit var defaultLanguage: String
    private var voice: TTS.Voice? = null
    private lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefs = Prefs.getInstance(this)
        tts = TTS.getInstance(this)

        seekBar = findViewById<SeekBar>(R.id.seekBar2)

        Log.i("Prueba", "Si ha iniciado la app")

        initButtons()
        stylesButtons()

        testVoices()
        btnSetVoices()

        initModifierSound()
    }

    fun stylesButtons(tema: String) {
        val red = findViewById<Button>(R.id.red)
        val yellow = findViewById<Button>(R.id.yellow)
        val cian = findViewById<Button>(R.id.cian)
        val blue = findViewById<Button>(R.id.blue)
        val green = findViewById<Button>(R.id.green)
        val pink = findViewById<Button>(R.id.pink)

        when (tema) {
            "light" -> {
                red.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red))
                yellow.setBackgroundColor(ContextCompat.getColor(this, R.color.light_orange))
                cian.setBackgroundColor(ContextCompat.getColor(this, R.color.light_cian))
                blue.setBackgroundColor(ContextCompat.getColor(this, R.color.light_purple))
                green.setBackgroundColor(ContextCompat.getColor(this, R.color.light_green))
                pink.setBackgroundColor(ContextCompat.getColor(this, R.color.light_pink))
            }
            "dark" -> {
                red.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_red))
                yellow.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_orange))
                cian.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_cian))
                blue.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_blue))
                green.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_green))
                pink.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_pink))
            }
        }
    }

    fun initButtons() {
        val typedValue = TypedValue()
        val theme = theme

        theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true)
        val colorOnPrimary = typedValue.data

        val darkBtn = findViewById<Button>(R.id.dark)
        val lightBtn = findViewById<Button>(R.id.light)

        val back = findViewById<ImageButton>(R.id.back)
        back.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var tema: String = ""

        var logicTheme: Array<Boolean> = arrayOf()

        val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                play.styleButton(darkBtn, this)
                logicTheme = arrayOf(false, true)
                tema = "dark"
                stylesButtons("dark")
                defaultTheme = "dark"
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                play.styleButton(lightBtn, this)
                logicTheme = arrayOf(true, false)
                tema = "light"
                stylesButtons("light")
                defaultTheme = "light"
            }
        }

        lightBtn.setOnClickListener {
            play.styleButton(lightBtn, this)
            darkBtn.setBackgroundColor(colorOnPrimary)
            tema = "light"
            logicTheme = arrayOf(true, false)
        }

        darkBtn.setOnClickListener {
            lightBtn.setBackgroundColor(colorOnPrimary)
            play.styleButton(darkBtn, this)
            tema = "dark"
            logicTheme = arrayOf(false, true)
        }

        val spain = findViewById<Button>(R.id.spanish)
        val english = findViewById<Button>(R.id.english)

        var logicLanguage: Array<Boolean> = arrayOf()

        val currentLocale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.resources.configuration.locales.get(0)
        } else {
            this.resources.configuration.locale
        }

        val languageCode = currentLocale.language
        var language: String = languageCode
        when (languageCode) {
            "es" -> {
                play.styleButton(spain, this)
                logicLanguage = arrayOf(true, false)
                defaultLanguage = "es"
            }
            "en" -> {
                play.styleButton(english, this)
                logicLanguage = arrayOf(false, true)
                defaultLanguage = "en"
            }
        }


        spain.setOnClickListener {
            english.setBackgroundColor(colorOnPrimary)
            play.styleButton(spain, this)
            language = "es"
            logicLanguage = arrayOf(true, false)
        }

        english.setOnClickListener {
            spain.setBackgroundColor(colorOnPrimary)
            play.styleButton(english, this)
            language = "en"
            logicLanguage = arrayOf(false, true)
        }

        val default = findViewById<Button>(R.id.defaultSettings)
        val apply = findViewById<Button>(R.id.aplly)

        apply.setOnClickListener {
            try {
                when (tema) {
                    "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                stylesButtons(tema)
                AppCompatDelegate.setApplicationLocales(androidx.core.os.LocaleListCompat.forLanguageTags(language))

                if (color != "") {
                    prefs.app.putStyle(color, this)
                }

                var buttons: Array<Button> = arrayOf()

                if (logicTheme[0]) {
                    if (logicLanguage[0])
                        buttons = arrayOf(default, apply, lightBtn, spain)
                    else
                        buttons = arrayOf(default, apply, lightBtn, english)
                } else {
                    if (logicLanguage[0])
                        buttons = arrayOf(default, apply, darkBtn, spain)
                    else
                        buttons = arrayOf(default, apply, darkBtn, english)
                }
                play.styleButton(buttons, this)

                voice?.let { prefs.app.putTTS(it) }

                seekBar.thumb.setTint(prefs.app.getStyle(this))

                tts.hablar("Todo ha sido cambiado correctamente")
                Toast.makeText(this, R.string.correct_apply, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "A ocurrido algún problema", Toast.LENGTH_SHORT).show()
            }

            Sound.playBackground(this, prefs.app.getBackgroundSound())

            Log.i("Prueba", prefs.app.getStyle(this).toString())
        }

        default.setOnClickListener {
            when (defaultTheme) {
                "dark" -> {
                    play.styleButton(darkBtn, this)
                    lightBtn.setBackgroundColor(colorOnPrimary)
                    tema = "dark"
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                "light" -> {
                    play.styleButton(lightBtn, this)
                    darkBtn.setBackgroundColor(colorOnPrimary)
                    tema = "light"
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

            when (defaultLanguage) {
                "es" -> {
                    play.styleButton(spain, this)
                    english.setBackgroundColor(colorOnPrimary)
                    language = "es"
                }
                "en" -> {
                    play.styleButton(english, this)
                    spain.setBackgroundColor(colorOnPrimary)
                    language = "en"
                }
            }

            Log.i("Prueba", defaultLanguage)
            AppCompatDelegate.setApplicationLocales(androidx.core.os.LocaleListCompat.forLanguageTags(defaultLanguage))

            prefs.app.putStyle(color, this, true)
            var buttons = arrayOf(default, apply)
            play.styleButton(buttons, this)
        }

        var buttons = arrayOf(default, apply)
        play.styleButton(buttons, this)
    }

    fun stylesButtons() {
        val red = findViewById<Button>(R.id.red)
        val orange = findViewById<Button>(R.id.yellow)
        val green = findViewById<Button>(R.id.green)
        val cian = findViewById<Button>(R.id.cian)
        val blue = findViewById<Button>(R.id.blue)
        val pink = findViewById<Button>(R.id.pink)

        val buttons = arrayOf(red, orange, green, cian, blue, pink)

        for (btn in buttons) {
            btn.setOnClickListener {
                when (btn) {
                    red -> color = "red"
                    orange -> color = "orange"
                    green -> color = "green"
                    cian -> color = "cian"
                    blue -> color = "blue"
                    pink -> color = "pink"
                }
                Log.i("Prueba", "Variable color cambiada: $color")
            }
        }
    }

    fun testVoices() {
        val testChico2 = findViewById<ImageButton>(R.id.testChico2)
        val testChica1 = findViewById<ImageButton>(R.id.testChica1)
        val testChica2 = findViewById<ImageButton>(R.id.testChica2)

        testChico2.setOnClickListener {
            tts.setVoz(tts.getVoice(TTS.Voice.CHICO1))
            tts.hablar("Empieza la jugada")
            tts.setVoz(prefs.app.getTTS())
        }

        testChica1.setOnClickListener {
            tts.setVoz(tts.getVoice(TTS.Voice.CHICA1))
            tts.hablar("Empieza la jugada")
            tts.setVoz(prefs.app.getTTS())
        }

        testChica2.setOnClickListener {
            tts.setVoz(tts.getVoice(TTS.Voice.CHICA2))
            tts.hablar("Empieza la jugada")
            tts.setVoz(prefs.app.getTTS())
        }
    }

    fun btnSetVoices() {
        val btnChico1 = findViewById<Button>(R.id.Chico2)
        val btnChica1 = findViewById<Button>(R.id.Chica1)
        val btnChica2 = findViewById<Button>(R.id.Chica2)

        btnChico1.setOnClickListener {
            voice = TTS.Voice.CHICO1
        }

        btnChica1.setOnClickListener {
            voice = TTS.Voice.CHICA1
        }

        btnChica2.setOnClickListener {
            voice = TTS.Voice.CHICA2
        }
    }

    @SuppressLint("WrongViewCast")
    fun initModifierSound() {
        val soundValue = findViewById<TextView>(R.id.soundValue)

        // Inicializar volumen
        seekBar.progress = prefs.app.getVolumeSound()
        seekBar.thumb.setTint(prefs.app.getStyle(this))
        Sound.setVolume(prefs.app.getVolumeSound())
        soundValue.text = prefs.app.getVolumeSound().toString()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                soundValue.text = progress.toString()
                Sound.setVolume(progress)
                prefs.app.putVolumeSound(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        findViewById<ImageButton>(R.id.testSound1).setOnClickListener {
            Sound.playPreview(this, R.raw.guitars_loops)
        }

        findViewById<ImageButton>(R.id.testSound2).setOnClickListener {
            Sound.playPreview(this, R.raw.lofi)
        }

        findViewById<ImageButton>(R.id.testSound3).setOnClickListener {
            Sound.playPreview(this, R.raw.sound4)
        }

        findViewById<ImageButton>(R.id.testSound4).setOnClickListener {
            Sound.playPreview(this, R.raw.sound_game_loops)
        }

        findViewById<Button>(R.id.playSound1).setOnClickListener {
            Sound.playBackground(this, R.raw.guitars_loops)
        }
        findViewById<Button>(R.id.playSound2).setOnClickListener {
            Sound.playBackground(this, R.raw.lofi)
        }
        findViewById<Button>(R.id.playSound3).setOnClickListener {
            Sound.playBackground(this, R.raw.sound4)
        }
        findViewById<Button>(R.id.playSound4).setOnClickListener {
            Sound.playBackground(this, R.raw.sound_game_loops)
        }
    }

    override fun onPause() {
        super.onPause()
        Sound.pauseBackground()
    }

    override fun onResume() {
        super.onResume()
        Sound.resumeBackground()
    }
}