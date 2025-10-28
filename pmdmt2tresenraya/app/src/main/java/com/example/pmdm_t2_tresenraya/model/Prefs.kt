package com.example.pmdm_t2_tresenraya.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

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

    val app = AppPrefs(appPrefs)
    val game = GamePrefs(gamePrefs)

    class AppPrefs(private val prefs: SharedPreferences) {

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

        fun putStyle(style: Int) {
            prefs.edit { putInt("style", style) }
        }
        fun getStyle(): Int {
            return prefs.getInt("style", 0)
        }
    }

    class GamePrefs(private val prefs: SharedPreferences) {
        val smallBoard = SmallBoard(prefs)
        val mediumBoard = MediumBoard(prefs)
        val bigBoard = BigBoard(prefs)
        class SmallBoard(private val prefs: SharedPreferences) {
            // Partidas jugadas
            fun putGamesPlayed() {
                var games = getGamesPlayed()
                games++
                prefs.edit {
                    putInt("smallBoardGamesPlayed", games)
                }
            }
            fun getGamesPlayed(): Int {
                return prefs.getInt("smallBoardGamesPlayed", 0)
            }

            // Puntaje de partidas ganadas el jugador 1
            fun putWinPlayer1() {
                var points = getWinPlayer1()
                points++
                prefs.edit {
                    putInt("smallBoardWinPlayer1", points)
                }
            }
            fun getWinPlayer1(): Int {
                return prefs.getInt("smallBoardWinPlayer1", 0)
            }

            // Puntaje de partidas ganadas el jugador 2
            fun putWinPlayer2() {
                var points = getWinPlayer2()
                points++
                prefs.edit {
                    putInt("smallBoardWinPlayer2", points)
                }
            }
            fun getWinPlayer2(): Int {
                return prefs.getInt("smallBoardWinPlayer2", 0)
            }

            // Puntaje de tablas
            fun putDraws() {
                var points = getDraws()
                points++
                prefs.edit {
                    putInt("smallDraw", points)
                }
            }
            fun getDraws(): Int {
                return prefs.getInt("smallDraw", 0)
            }

            // Reiniciar todos los datos
            fun restartAll() {
                prefs.edit {
                    putInt("smallBoardGamesPlayed", 0)
                }
                prefs.edit {
                    putInt("smallBoardWinPlayer1", 0)
                }
                prefs.edit {
                    putInt("smallBoardWinPlayer2", 0)
                }
                prefs.edit {
                    putInt("smallDraw", 0)
                }
            }
        }

        class MediumBoard(private val prefs: SharedPreferences) {
            // Partidas jugadas
            fun putGamesPlayed() {
                var games = getGamesPlayed()
                games++
                prefs.edit {
                    putInt("mediumBoardGamesPlayed", games)
                }
            }
            fun getGamesPlayed(): Int {
                return prefs.getInt("mediumBoardGamesPlayed", 0)
            }

            // Puntaje de partidas ganadas el jugador 1
            fun putWinPlayer1() {
                var points = getWinPlayer1()
                points++
                prefs.edit {
                    putInt("mediumBoardWinPlayer1", points)
                }
            }
            fun getWinPlayer1(): Int {
                return prefs.getInt("mediumBoardWinPlayer1", 0)
            }

            // Puntaje de partidas ganadas el jugador 2
            fun putWinPlayer2() {
                var points = getWinPlayer2()
                points++
                prefs.edit {
                    putInt("mediumBoardWinPlayer2", points)
                }
            }
            fun getWinPlayer2(): Int {
                return prefs.getInt("mediumBoardWinPlayer2", 0)
            }

            // Puntaje de tablas
            fun putDraws() {
                var points = getDraws()
                points++
                prefs.edit {
                    putInt("mediumDraw", points)
                }
            }
            fun getDraws(): Int {
                return prefs.getInt("mediumDraw", 0)
            }

            // Reiniciar todos los datos
            fun restartAll() {
                prefs.edit {
                    putInt("mediumBoardGamesPlayed", 0)
                }
                prefs.edit {
                    putInt("mediumBoardWinPlayer1", 0)
                }
                prefs.edit {
                    putInt("mediumBoardWinPlayer2", 0)
                }
                prefs.edit {
                    putInt("mediumDraw", 0)
                }
            }
        }

        class BigBoard(private val prefs: SharedPreferences) {
            // Partidas jugadas
            fun putGamesPlayed() {
                var games = getGamesPlayed()
                games++
                prefs.edit {
                    putInt("bigBoardGamesPlayed", games)
                }
            }
            fun getGamesPlayed(): Int {
                return prefs.getInt("bigBoardGamesPlayed", 0)
            }

            // Puntaje de partidas ganadas el jugador 1
            fun putWinPlayer1() {
                var points = getWinPlayer1()
                points++
                prefs.edit {
                    putInt("bigBoardWinPlayer1", points)
                }
            }
            fun getWinPlayer1(): Int {
                return prefs.getInt("bigBoardWinPlayer1", 0)
            }

            // Puntaje de partidas ganadas el jugador 2
            fun putWinPlayer2() {
                var points = getWinPlayer2()
                points++
                prefs.edit {
                    putInt("bigBoardWinPlayer2", points)
                }
            }
            fun getWinPlayer2(): Int {
                return prefs.getInt("bigBoardWinPlayer2", 0)
            }

            // Puntaje de tablas
            fun putDraws() {
                var points = getDraws()
                points++
                prefs.edit {
                    putInt("bigDraw", points)
                }
            }
            fun getDraws(): Int {
                return prefs.getInt("bigDraw", 0)
            }

            // Reiniciar todos los datos
            fun restartAll() {
                prefs.edit {
                    putInt("bigBoardGamesPlayed", 0)
                }
                prefs.edit {
                    putInt("bigBoardWinPlayer1", 0)
                }
                prefs.edit {
                    putInt("bigBoardWinPlayer2", 0)
                }
                prefs.edit {
                    putInt("bigDraw", 0)
                }
            }
        }
    }
}
