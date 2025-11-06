package com.example.pmdm_t2_tresenraya.controller

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper

object Sound {

    private var mediaPlayer: MediaPlayer? = null
    private var currentSoundResId: Int? = null
    private var isPreviewPlaying = false

    private var volume: Float = 1.0f // entre 0.0 y 1.0

    /**
     * Reproduce una canción en bucle como música de fondo.
     */
    fun playBackground(context: Context, soundResId: Int) {
        if (currentSoundResId == soundResId && mediaPlayer?.isPlaying == true) return

        stopBackground()

        mediaPlayer = MediaPlayer.create(context.applicationContext, soundResId).apply {
            isLooping = true
            setVolume(volume, volume)
            start()
        }

        currentSoundResId = soundResId
    }

    /**
     * Reproduce una vista previa de 10 segundos.
     */
    fun playPreview(context: Context, soundResId: Int, durationMs: Long = 10_000L) {
        if (isPreviewPlaying) return // Evitar superposición

        isPreviewPlaying = true

        // 🔹 Pausar la música de fondo si está sonando
        val wasPlayingBackground = mediaPlayer?.isPlaying == true
        if (wasPlayingBackground) {
            mediaPlayer?.pause()
        }

        val previewPlayer = MediaPlayer.create(context.applicationContext, soundResId)
        previewPlayer.setVolume(volume, volume)
        previewPlayer.start()

        // 🔹 Escuchar cuándo termina el preview (automático)
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                previewPlayer.stop()
            } catch (_: Exception) { /* ignora si ya se detuvo */ }

            previewPlayer.release()
            isPreviewPlaying = false

            // 🔹 Reanudar música de fondo si estaba pausada antes
            if (wasPlayingBackground) {
                mediaPlayer?.start()
            }
        }, durationMs)
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
    fun stopBackground() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        currentSoundResId = null
    }

    /**
     * Llama esto desde onPause() o cuando cierres la app si quieres pausar la música.
     */
    fun pauseBackground() {
        mediaPlayer?.pause()
    }

    fun resumeBackground() {
        mediaPlayer?.start()
    }
}
