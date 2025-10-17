package com.example.pmdm_t2_tresenraya

import android.app.Activity
import android.util.TypedValue
import android.widget.Button

class Play (val activity: Activity) {

    fun setBaseColor(celda: Array<Array<Button>>) {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true)
        val colorFromTheme = typedValue.data


        for (row in celda) {
            for (colunm in row) {
                colunm.setBackgroundColor(colorFromTheme)
            }
        }
    }

    fun setX(btn: Button, posiciones: Array<Array<Char>>, posX: Int, posY: Int): Array<Array<Char>> {
        if (posiciones[posY][posX] == ' ') {
            btn.setBackgroundResource(R.drawable.celda_pulsada_x)
        }
        posiciones[posY][posX] = 'X'
        return posiciones
    }

    fun setO(btn: Button, posiciones: Array<Array<Char>>, posY: Int, posX: Int): Array<Array<Char>> {
        if (posiciones[posY][posX] == ' ') {
            btn.setBackgroundResource(R.drawable.celda_pulsada_o)
        }
        posiciones[posY][posX] = 'O'
        return posiciones
    }
}