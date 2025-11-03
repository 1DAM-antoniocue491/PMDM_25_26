package com.example.pmdm_t2_tresenraya.model

import android.R.attr.theme
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.Log
import android.util.TypedValue
import androidx.core.content.edit
import androidx.core.content.ContextCompat
import com.example.pmdm_t2_tresenraya.R

class Prefs private constructor(context: Context) {
    companion object {
        @Volatile
        private var instance: Prefs? = null

        fun getInstance(context: Context): Prefs {
            return instance ?: synchronized(this) {
                instance ?: Prefs(context.applicationContext).also { instance = it }
            }
        }
    }

    private val appPrefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    private val gamePrefs = context.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)


    val app = AppPrefs(appPrefs, context)
    val game = GamePrefs(gamePrefs)

    class AppPrefs(private val prefs: SharedPreferences, context: Context) {
        val tts = TTS.getInstance(context)
        fun putStart(player: String) {
            prefs.edit { putString("start", player) }
        }
        fun getStart(): String {
            return prefs.getString("start", "player1") ?: "player1"
        }

        fun putGameMode(mode: String) {
            prefs.edit { putString("gameMode", mode) }
        }
        fun getGameMode(): String {
            return prefs.getString("gameMode", "true") ?: "true"
        }

        fun putTableSize(table: Int) {
            prefs.edit { putInt("tableSize", table) }
        }
        fun getTableSize(): Int {
            return prefs.getInt("tableSize", 3)
        }

        fun setDarkMode(mode: Boolean) {
            prefs.edit { putBoolean("darkMode", mode) }
        }
        fun isDarkMode(): Boolean {
            return prefs.getBoolean("darkMode", false)
        }

        fun putLanguage(language: String) {
            prefs.edit { putString("language", language) }
        }
        fun getLanguage(): String {
            return prefs.getString("language", "es") ?: "es"
        }

        fun putStyle(color: String, context: Context, defaultColor: Boolean = false) {
            var colorFinal: String? = ""

            Log.i("Prueba", "Color a cuardar: $color")

            if (defaultColor) {
                Log.i("Prueba", "El color por defecto se ha activado")
                colorFinal = "cian"
            } else
                colorFinal = color

            prefs.edit {
                putString("style", colorFinal)
            }
        }

        fun getStyle(context: Context): Int {
            val theme = getTheme(context)

            return when (prefs.getString("style", "")) {
                "red" -> {
                    if (theme)
                        ContextCompat.getColor(context, R.color.dark_red)
                    else
                        ContextCompat.getColor(context, R.color.light_red)
                }
                "orange" -> {
                    if (theme)
                        ContextCompat.getColor(context, R.color.dark_orange)
                    else
                        ContextCompat.getColor(context, R.color.light_orange)
                }
                "green" -> {
                    if (theme)
                        ContextCompat.getColor(context, R.color.dark_green)
                    else
                        ContextCompat.getColor(context, R.color.light_green)
                }
                "cian" -> {
                    if (theme)
                        ContextCompat.getColor(context, R.color.dark_cian)
                    else
                        ContextCompat.getColor(context, R.color.light_cian)
                }
                "blue" -> {
                    if (theme)
                        ContextCompat.getColor(context, R.color.dark_blue)
                    else
                        ContextCompat.getColor(context, R.color.light_purple)
                }
                "pink" -> {
                    if (theme)
                        ContextCompat.getColor(context, R.color.dark_pink)
                    else
                        ContextCompat.getColor(context, R.color.light_pink)
                }

                else -> {
                    if (theme)
                        ContextCompat.getColor(context, R.color.dark_cian)
                    else
                        ContextCompat.getColor(context, R.color.light_cian)
                }
            }
        }

        private fun getTheme(context: Context): Boolean {
            val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (nightModeFlags) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    return true
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    return false
                }
            }
            return true
        }

        fun putTTS(voice: TTS.Voice) {
            prefs.edit() {
                putString("tts_voice", tts.getVoice(voice))
            }
        }

        fun getTTS(): String? {
            return prefs.getString("tts_voice", tts.getVoice(TTS.Voice.CHICO1))
        }
    }

    class GamePrefs(private val prefs: SharedPreferences) {
        val pvp = PVP(prefs)
        val iap = IA(prefs)

        class PVP(private val prefs: SharedPreferences) {
            // Partidas jugadas
            fun putGamesPlayed() {
                var games = getGamesPlayed()
                games++
                prefs.edit {
                    putInt("game_PVP", games)
                }
            }
            fun getGamesPlayed(): Int {
                return prefs.getInt("game_PVP", 0)
            }

            // Puntaje de partidas ganadas el jugador 1
            fun putWinPlayer1() {
                var points = getWinPlayer1()
                points++
                prefs.edit {
                    putInt("win1_PVP", points)
                }
            }
            fun getWinPlayer1(): Int {
                return prefs.getInt("win1_PVP", 0)
            }

            // Puntaje de partidas ganadas el jugador 2
            fun putWinPlayer2() {
                var points = getWinPlayer2()
                points++
                prefs.edit {
                    putInt("win2_PVP", points)
                }
            }
            fun getWinPlayer2(): Int {
                return prefs.getInt("win2_PVP", 0)
            }

            // Puntaje de tablas
            fun putDraws() {
                var points = getDraws()
                points++
                prefs.edit {
                    putInt("draws_PVP", points)
                }
            }
            fun getDraws(): Int {
                return prefs.getInt("draws_PVP", 0)
            }

            // Reiniciar todos los datos
            fun restartAll() {
                prefs.edit {
                    putInt("game_PVP", 0)
                }
                prefs.edit {
                    putInt("win1_PVP", 0)
                }
                prefs.edit {
                    putInt("win2_PVP", 0)
                }
                prefs.edit {
                    putInt("draws_PVP", 0)
                }
            }
        }

        class IA(private val prefs: SharedPreferences) {
            // Partidas jugadas
            fun putGamesPlayed() {
                var games = getGamesPlayed()
                games++
                prefs.edit {
                    putInt("game_IAP", games)
                }
            }
            fun getGamesPlayed(): Int {
                return prefs.getInt("game_IAP", 0)
            }

            // Puntaje de partidas ganadas el jugador 1
            fun putWinPlayer() {
                var points = getWinPlayer()
                points++
                prefs.edit {
                    putInt("win1_IAP", points)
                }
            }
            fun getWinPlayer(): Int {
                return prefs.getInt("win1_IAP", 0)
            }

            // Puntaje de partidas ganadas el jugador 2
            fun putWinIA() {
                var points = getWinIA()
                points++
                prefs.edit {
                    putInt("win2_IAP", points)
                }
            }
            fun getWinIA(): Int {
                return prefs.getInt("win2_IAP", 0)
            }

            // Puntaje de tablas
            fun putDraws() {
                var points = getDraws()
                points++
                prefs.edit {
                    putInt("draws_IAP", points)
                }
            }
            fun getDraws(): Int {
                return prefs.getInt("draws_IAP", 0)
            }

            // Reiniciar todos los datos
            fun restartAll() {
                prefs.edit {
                    putInt("game_IAP", 0)
                }
                prefs.edit {
                    putInt("win1_IAP", 0)
                }
                prefs.edit {
                    putInt("win2_IAP", 0)
                }
                prefs.edit {
                    putInt("draws_IAP", 0)
                }
            }
        }
    }
}
