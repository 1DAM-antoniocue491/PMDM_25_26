package com.example.pmdm_t2_tresenraya

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_t2_tresenraya.R
import java.util.Locale


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initButtons()
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

        theme.resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValue, true)
        val colorSecondary = typedValue.data

        theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true)
        val colorOnPrimary = typedValue.data


        val darkBtn = findViewById<Button>(R.id.dark)
        val lightBtn = findViewById<Button>(R.id.light)

        var tema: String = ""

        val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                darkBtn.setBackgroundColor(colorSecondary)
                tema = "dark"
                stylesButtons("dark")
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                lightBtn.setBackgroundColor(colorSecondary)
                tema = "light"
                stylesButtons("light")
            }
        }

        lightBtn.setOnClickListener {
            lightBtn.setBackgroundColor(colorSecondary)
            darkBtn.setBackgroundColor(colorOnPrimary)
            tema = "light"
        }

        darkBtn.setOnClickListener {
            lightBtn.setBackgroundColor(colorOnPrimary)
            darkBtn.setBackgroundColor(colorSecondary)
            tema = "dark"
        }

        val spain = findViewById<Button>(R.id.spanish)
        val english = findViewById<Button>(R.id.english)

        val currentLocale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.resources.configuration.locales.get(0)
        } else {
            this.resources.configuration.locale
        }

        val languageCode = currentLocale.language
        var language: String = languageCode
        when (languageCode) {
            "es" -> spain.setBackgroundColor(colorSecondary)
            "en" -> english.setBackgroundColor(colorSecondary)
        }


        spain.setOnClickListener {
            english.setBackgroundColor(colorOnPrimary)
            spain.setBackgroundColor(colorSecondary)
            language = "es"
        }

        english.setOnClickListener {
            spain.setBackgroundColor(colorOnPrimary)
            english.setBackgroundColor(colorSecondary)
            language = "en"
        }

        val apply = findViewById<Button>(R.id.aplly)
        apply.setOnClickListener {
            when (tema) {
                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            stylesButtons(tema)
            AppCompatDelegate.setApplicationLocales(androidx.core.os.LocaleListCompat.forLanguageTags(language))
        }

        val default = findViewById<Button>(R.id.defaultSettings)
        default.setOnClickListener {
            val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (nightModeFlags) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    darkBtn.setBackgroundColor(colorSecondary)
                    lightBtn.setBackgroundColor(colorOnPrimary)
                    tema = "dark"
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    lightBtn.setBackgroundColor(colorSecondary)
                    darkBtn.setBackgroundColor(colorOnPrimary)
                    tema = "light"
                }
            }

            when (languageCode) {
                "es" -> {
                    spain.setBackgroundColor(colorSecondary)
                    english.setBackgroundColor(colorOnPrimary)
                    language = "es"
                }
                "en" -> {
                    english.setBackgroundColor(colorSecondary)
                    spain.setBackgroundColor(colorOnPrimary)
                    language = "en"
                }
            }

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.setApplicationLocales(androidx.core.os.LocaleListCompat.forLanguageTags(languageCode))
        }
    }
}