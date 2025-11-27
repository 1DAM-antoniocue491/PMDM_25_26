package com.mm.simonapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import androidx.viewbinding.ViewBinding
import kotlin.random.Random

class Controller (btnStart: Button, activity: Activity) {
    private val bntStart = btnStart
    private val context = activity
    private var secuence: ArrayList<ButtonColor> = arrayListOf()
    private var level: Int = 1

    fun start() {
        when (this.bntStart.text) {
            "Start" -> this.bntStart.text = "Stop"
            "Stop" -> this.bntStart.text = "Start"
        }
    }

    private fun highlightButton(button: ImageButton) {
        button.isPressed = true
        button.invalidate()

        Handler(Looper.getMainLooper()).postDelayed({
            button.isPressed = false
            button.invalidate()
        }, 300)
    }

    fun getButton(btn: ButtonColor): ImageButton {
        return when (btn) {
            ButtonColor.RED -> this.context.findViewById(R.id.btnRed)
            ButtonColor.GREEN -> this.context.findViewById(R.id.btnGreen)
            ButtonColor.YELLOW -> this.context.findViewById(R.id.btnYellow)
            ButtonColor.BLUE -> this.context.findViewById(R.id.btnBlue)
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

    fun generateSecuence(): ArrayList<ButtonColor> {
        val color: ButtonColor = ButtonColor.values().random()
        secuence.add(color)

        return secuence
    }

}