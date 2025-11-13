package com.example.pmdm_t2_tresenraya.model

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper

object Sound {

    private var mediaPlayer: MediaPlayer? = null
    private var currentSoundResId: Int? = null
    private var isPreviewPlaying = false

    private var volume: Float = 1.0f // entre 0.0 y 1.0
    private var previewPlayer: MediaPlayer? = null

    /**
     * Reproduce una canción en bucle como música de fondo.
     */
    fun playBackground(context: Context, soundResId: Int) {
        if (currentSoundResId == soundResId && mediaPlayer?.isPlaying == true && Prefs.getInstance(context).app.isBackgroundSound()) return

        stopBackground(context)
        stopPreview(false)

        mediaPlayer = MediaPlayer.create(context.applicationContext, soundResId).apply {
            isLooping = true
            setVolume(volume, volume)
            start()
        }

        Prefs.getInstance(context).app.putBackgroundSound(soundResId)

        currentSoundResId = soundResId

        Prefs.getInstance(context).app.putIsBackgroundSound(true)
    }

    /**
     * Reproduce una vista previa de 10 segundos.
     */
    fun playPreview(context: Context, soundResId: Int, durationMs: Long = 10_000L) {
        // 🔹 Si ya hay un preview sonando, detenerlo antes de reproducir el nuevo
        if (isPreviewPlaying) {
            try {
                previewPlayer?.stop()
            } catch (_: Exception) { }
            previewPlayer?.release()
            previewPlayer = null
            isPreviewPlaying = false
        }

        // 🔹 Pausar la música de fondo si está sonando
        val wasPlayingBackground = mediaPlayer?.isPlaying == true
        if (wasPlayingBackground) {
            mediaPlayer?.pause()
        }

        // 🔹 Crear el nuevo MediaPlayer para el preview
        previewPlayer = MediaPlayer.create(context.applicationContext, soundResId).apply {
            setVolume(volume, volume)
            start()
        }
        isPreviewPlaying = true

        // 🔹 Programar la parada automática
        Handler(Looper.getMainLooper()).postDelayed({
            stopPreview(wasPlayingBackground)
        }, durationMs)
    }

    private fun stopPreview(wasPlayingBackground: Boolean) {
        try {
            previewPlayer?.stop()
        } catch (_: Exception) { }

        previewPlayer?.release()
        previewPlayer = null
        isPreviewPlaying = false

        // 🔹 Reanudar música de fondo si estaba pausada
        if (wasPlayingBackground) {
            mediaPlayer?.start()
        }
    }


    /**
     * Cambia el volumen (0-100 del SeekBar → 0.0-1.0 para MediaPlayer)
     */
    fun setVolume(progress: Int) {
        volume = progress / 100f
        mediaPlayer?.setVolume(volume, volume)
    }

    /**
     * Detiene y libera la música de fondo.
     */
    fun stopBackground(context: Context) {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        currentSoundResId = null
        Prefs.getInstance(context).app.putIsBackgroundSound(false)
    }

    /**
     * Llama esto desde onPause() o cuando cierres la app si quieres pausar la música.
     */
    fun pauseBackground(context: Context) {
        mediaPlayer?.pause()
        Prefs.getInstance(context).app.putIsBackgroundSound(false)
    }

    fun resumeBackground(context: Context) {
        mediaPlayer?.start()
        Prefs.getInstance(context).app.putIsBackgroundSound(true)
    }
}
