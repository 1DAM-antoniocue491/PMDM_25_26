package com.mm.simonapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.random.Random

class Controller (btnStart: Button, activity: Activity) {
    private val bntStart = btnStart
    private val context = activity
    private var secuence: ArrayList<ButtonColor> = arrayListOf()
    private var level: Int = 1
    private var function: Boolean = false

    fun start() {
        when (this.bntStart.text) {
            "Start" -> {
                this.bntStart.text = "Stop"
                function = true
            }
            "Stop" -> {
                this.bntStart.text = "Start"
                function = false
            }
        }
    }

    fun getButton(btn: ButtonColor): ImageButton {
        return when (btn) {
            ButtonColor.RED -> this.context.findViewById(R.id.btnRed)
            ButtonColor.GREEN -> this.context.findViewById(R.id.btnGreen)
            ButtonColor.YELLOW -> this.context.findViewById(R.id.btnYellow)
            ButtonColor.BLUE -> this.context.findViewById(R.id.btnBlue)
        }
    }

    fun getSound(btn: ButtonColor): Int {
        return when (btn) {
            ButtonColor.RED -> R.raw.do_nota
            ButtonColor.GREEN -> R.raw.re
            ButtonColor.YELLOW -> R.raw.fa
            ButtonColor.BLUE -> R.raw.sol
        }
    }

    fun getButton(btn: ImageButton): ButtonColor {
        return when (btn) {
            this.context.findViewById(R.id.btnRed) -> ButtonColor.RED
            this.context.findViewById(R.id.btnGreen) -> ButtonColor.GREEN
            this.context.findViewById(R.id.btnYellow) -> ButtonColor.YELLOW
            this.context.findViewById(R.id.btnBlue) -> ButtonColor.BLUE
            else -> {
                ButtonColor.BLUE}
        }
    }

    fun playSound(context: Context, soundResId: Int) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.release()
        }
    }

    fun generateSecuence() {
        val color: ButtonColor = ButtonColor.values().random()
        secuence.add(color)
    }

    fun checkSequence(check: List<ButtonColor>): Boolean {
        var result = false

        if (check == secuence) {
            result = true;
            level++
        } else {
            level = 0
            function = false
        }

        return result
    }

    fun getFunction(): Boolean {
        return function
    }
    fun getLevel(): Int {
        return level
    }

    suspend fun clicButton(btn: ButtonColor) {
        val button: ImageButton = getButton(btn)
        buttonCliked(button)
    }

    fun playSound(btn: ButtonColor) {
        val sound = getSound(btn)
        playSound(context, sound)
    }

    suspend fun playSecuence (scope: CoroutineScope) {
        for (btn in secuence) {
            scope.launch {
                coroutineScope {
                    awaitAll(
                        async { clicButton(btn) },
                        async { playSound(btn) }
                    )
                }
            }

            delay(2000)
        }
    }

    fun highlightDrawable(): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(0xAAFFFFFF.toInt()) // brillo
            setSize(200, 200)
        }
    }

    suspend fun buttonCliked(button: ImageButton) {
        val normal = button.background
        val highlight = highlightDrawable()

        button.background = highlight

        delay(200) // tiempo iluminado

        button.background = normal
    }

}