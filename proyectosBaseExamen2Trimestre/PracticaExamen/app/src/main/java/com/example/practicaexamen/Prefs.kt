package com.mm.proyectofinal.data.local

import android.content.Context
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

    private val prefs = context.getSharedPreferences("examen", Context.MODE_PRIVATE)


    fun getName(): String? {
        return prefs.getString("name", "")
    }

    fun putName(nombre: String) {
        prefs.edit {
            putString("name", nombre)
        }
    }

    fun getEmail(): String? {
        return prefs.getString("email", "")
    }

    fun putEmail(email: String) {
        prefs.edit {
            putString("email", email)
        }
    }

    fun putTipo(tipo: String) {
        prefs.edit {
            putString("tipo", tipo)
        }
    }

    fun logout() {
        prefs.edit {
            putString("name", "")
            putString("email", "")
            putString("tipo", "")
        }
    }


}