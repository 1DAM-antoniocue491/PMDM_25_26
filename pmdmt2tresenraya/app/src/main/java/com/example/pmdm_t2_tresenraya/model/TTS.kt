package com.example.pmdm_t2_tresenraya.model

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TTS private constructor(context: Context): TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var inicializado = false
    private var language: String

    init {
        tts = TextToSpeech(context.applicationContext, this)
        language = Locale.getDefault().language
    }

    enum class Voice {
        CHICO1,
        CHICA1,
        CHICA2
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


    fun hablar(text: String, language: String) {
        this.language = language

        when (language) {
            "es" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                    this.tts?.language = Locale.of(language.uppercase(), language)
                } else {
                    this.tts?.language = Locale(language, language.uppercase())
                }
            }
            "en" -> this.tts?.language = Locale.US
        }
        if (inicializado) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_ID")
        } else {
            Log.w("TTS", "TTS aún no ha iniciado")
        }
    }

    fun getVoice(voice: Voice): String {
        // Español
        // Chico 1: tts.setVoz("es-es-x-eef-local")
        // Chica 1: tts.setVoz("es-es-x-eea-network")
        // Chica 2: tts.setVoz("es-ES-language")

        // Ingles
        // Voz 1: en-us-x-tpf-local
        // Voz 2: en-us-x-sfg-local
        // Voz 3: en-us-x-iob-local

        Log.i("Prueba", language)

        return when (language) {
            "es" -> {
                when (voice) {
                    Voice.CHICO1 -> "es-es-x-eef-local"
                    Voice.CHICA1 -> "es-es-x-eea-network"
                    Voice.CHICA2 -> "es-es-x-eed-local"
                    else -> "es-es-x-eef-local"
                }
            }
            "en" -> {
                when (voice) {
                    Voice.CHICO1 -> "en-gb-x-rjs-local"
                    Voice.CHICA1 -> "en-us-x-sfg-network"
                    Voice.CHICA2 -> "en-gb-x-gba-local"
                    else -> "en-us-x-sfg-local"
                }
            }
            else -> "es-es-x-eef-local" // idioma por defecto
        }

    }

    fun setVoz(nombreVoz: String?) {
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