package com.example.pmdm_t2_tresenraya.model

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.widget.Button
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

    fun setX(btn_id: Int) {
        val symbol = Symbols()
        val btn: Button = activity.findViewById<Button>(btn_id)
        //btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.x_3_3)
        //btn.background = symbol.XSymbol()
        btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.x_symbol)
    }

    fun setO(btn_id: Int) {
        val symbol = Symbols()
        val btn: Button = activity.findViewById<Button>(btn_id)
        //btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.o_3_3)
        //btn.background = symbol.OSymbol()
        btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.circle)
    }
}