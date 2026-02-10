package com.example.pmdm_t3_configapp

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.content.ContextCompat
import java.util.Locale
import kotlin.also

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

    private val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    private var numModificaciones: Int = 0

    fun putData(email: String, passwd: String, context: Context) {
        if (email != "" && passwd != "") {
            prefs.edit {
                putString("email", email)
                putString("passwd", passwd)
            }
        }
        numModificaciones++
        Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
    }

    fun getData (): Array<String?> {
        val email: String? = prefs.getString("email", "")
        val passwd: String? = prefs.getString("passwd", "")

        return arrayOf(email, passwd)
    }
}
