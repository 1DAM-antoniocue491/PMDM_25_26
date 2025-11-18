package com.example.pmdm_t2_tresenraya.model

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.Log
import androidx.core.content.edit
import androidx.core.content.ContextCompat
import com.example.pmdm_t2_tresenraya.R
import java.util.Locale

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
        fun putStart(player: Boolean) {
            prefs.edit { putBoolean("start", player) }
        }
        fun getStart(): Boolean {
            return prefs.getBoolean("start", true)
        }

        fun putGameMode(mode: String) {
            prefs.edit { putString("gameMode", mode) }
        }
        fun getGameMode(): String {
            return prefs.getString("gameMode", "true") ?: "true"
        }

        fun setDarkMode(mode: Boolean) {
            prefs.edit { putBoolean("darkMode", mode) }
        }
        fun isDarkMode(context: Context): Boolean {
            return prefs.getBoolean("darkMode", getMode(context))
        }

        private fun getMode(context: Context): Boolean {
            val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

            when (nightModeFlags) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    return true
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    return false
                }
                else -> {
                    return false
                }
            }

        }

        fun putLanguage(language: String) {
            prefs.edit { putString("language", language) }
        }
        fun getLanguage(): String {
            val language = Locale.getDefault().language

            return prefs.getString("language", language) ?: language
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

        fun getTheme(context: Context): Boolean {
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

        fun restartLevels() {
            prefs.edit {
                putStringSet("levels", setOf())
            }
        }

        fun putBackgroundSound(sound: Int) {
            prefs.edit {
                putInt("backgroundSound", sound)
            }
        }

        fun getBackgroundSound(): Int {
            return prefs.getInt("backgroundSound", R.raw.lofi)
        }

        fun putVolumeSound(volume: Int) {
            prefs.edit{
                putInt("volumeSound", volume)
            }
        }

        fun getVolumeSound(): Int {
            return prefs.getInt("volumeSound", 100)
        }

        fun isBackgroundSound(): Boolean {
            return prefs.getBoolean("isBackgroundSound", false)
        }

        fun putIsBackgroundSound(sound: Boolean) {
            prefs.edit {
                putBoolean("isBackgroundSound", sound)
            }
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
            enum class Modifier {
                PLUS,
                LESS,
                EQUALS
            }

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
                prefs.edit {
                    putInt("levelsPoint", 0)
                }
                prefs.edit {
                    putInt("previousLevel", 0)
                }
            }

            fun modifyPoint(modifier: IA.Modifier) {
                var point = getPoints()
                if (point != 0 || modifier == IA.Modifier.PLUS) {
                    when (modifier) {
                        IA.Modifier.PLUS -> point += 4
                        IA.Modifier.LESS -> point -= 2
                        IA.Modifier.EQUALS -> point--
                    }
                }
                prefs.edit {
                    putInt("levelsPoint", point)
                }
            }

            fun getPoints(): Int {
                return prefs.getInt("levelsPoint", 0)
            }

            fun putPreviousLevel(level: Int) {
                prefs.edit {
                    putInt("previousLevel", level)
                }
            }

            fun getPreviousLevel(): Int {
                return prefs.getInt("previousLevel", 0)
            }
        }

    }
}
