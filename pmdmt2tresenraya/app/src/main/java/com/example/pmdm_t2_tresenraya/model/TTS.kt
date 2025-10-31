package com.example.pmdm_t2_tresenraya.model

import android.app.Activity
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.ui.unit.TextUnit
import java.util.Locale

class TTS private constructor(context: Context): TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var inicializado = false

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.getDefault())

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "El idioma no es soportado o faltan datos")
            } else {
                inicializado = true
                Log.d("TTS", "TextToSpeech inicializado correctamente")
            }
        } else {
            Log.e("TTS", "Error al iniciar TTS")
        }
    }

    fun hablar(text: String) {
        if (inicializado) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_ID")
        } else {
            Log.w("TTS", "TTS aún no ha iniciado")
        }
    }

    fun getVoices() {
        val voces = tts?.voices
        voces?.forEach { voice ->
            if (voice.name.startsWith("es-es") || voice.name.startsWith("en-us"))
            Log.d("TTS", "Voz: ${voice.name}, idioma: ${voice.locale}, gender: ${voice.features}")
        }
    }

    fun setVoz(nombreVoz: String) {
        val voice = tts?.voices?.find { it.name.equals(nombreVoz, ignoreCase = true) }
        if (voice != null) {
            tts?.voice = voice
            Log.d("TTS", "Voz cambiada a ${voice.name}")
        } else {
            Log.w("TTS", "Voz no encontrada: $nombreVoz")
        }
    }

    fun liberar() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        inicializado = false
    }

    companion object{
        private var instance: TTS? = null

        fun getInstance(context: Context): TTS {
            if (instance == null) {
                instance = TTS(context)
            }
            return instance!!
        }

        fun destroy() {
            instance?.liberar()
            instance = null
        }
    }
}