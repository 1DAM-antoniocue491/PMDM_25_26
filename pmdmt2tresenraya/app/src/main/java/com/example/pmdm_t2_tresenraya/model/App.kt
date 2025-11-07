package com.example.pmdm_t2_tresenraya.model

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class App : Application(), DefaultLifecycleObserver {

    override fun onCreate() {
        super<Application>.onCreate()
        // Escuchar el ciclo de vida global de la app
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStop(owner: LifecycleOwner) {
        // La app se va a segundo plano (usuario salió)
        Sound.pauseBackground()
    }

    override fun onStart(owner: LifecycleOwner) {
        // La app vuelve a primer plano
        Sound.resumeBackground()
    }
}