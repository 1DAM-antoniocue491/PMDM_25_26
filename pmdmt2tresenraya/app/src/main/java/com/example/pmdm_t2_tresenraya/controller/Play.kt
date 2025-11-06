package com.example.pmdm_t2_tresenraya.controller

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import androidx.core.content.ContextCompat
import com.google.android.material.R

class Play (val activity: Activity) {
    val prefs = Prefs.getInstance(activity)

    fun setBaseColor(celda: Button?) {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
        val colorFromTheme = typedValue.data

        celda?.setBackgroundColor(colorFromTheme)
    }

    fun styleButton(buttons: Array<Button>, context: Context) {
        val color = prefs.app.getStyle(context)

        for (btn in buttons) {
            Log.i("Prueba", "Color al escribir: $color - ID del btn: ${btn.text}")
            btn.setBackgroundColor(color)
        }
    }

    fun styleButton(button: Button, context: Context) {
        val color = prefs.app.getStyle(context)

        button.setBackgroundColor(color)
    }

    fun setX(btn_id: Int, context: Context) {
        val btn: Button = activity.findViewById<Button>(btn_id)
        btn.background = ContextCompat.getDrawable(context, com.example.pmdm_t2_tresenraya.R.drawable.x_symbol)
    }

    fun setO(btn_id: Int, context: Context) {
        val btn: Button = activity.findViewById<Button>(btn_id)
        btn.background = ContextCompat.getDrawable(context, com.example.pmdm_t2_tresenraya.R.drawable.circle)
    }
}