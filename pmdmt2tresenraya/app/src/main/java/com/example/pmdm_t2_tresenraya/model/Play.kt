package com.example.pmdm_t2_tresenraya.model

import android.app.Activity
import android.util.TypedValue
import android.widget.Button
import com.google.android.material.R

class Play (val activity: Activity) {

    fun setBaseColor(celda: Array<Array<Button>>) {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
        val colorFromTheme = typedValue.data


        for (row in celda) {
            for (colunm in row) {
                colunm.setBackgroundColor(colorFromTheme)
            }
        }
    }

    fun setX(btn_id: Int, posiciones: Array<Array<Char>>, posX: Int, posY: Int): Array<Array<Char>> {
        val btn: Button = activity.findViewById<Button>(btn_id)
        if (posiciones[posY][posX] == ' ') {
            when(posiciones.size) {
                3 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.x_3_3)
                6 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.x_6_6)
                9 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.x_9_9)
            }
        }
        return posiciones
    }

    fun setO(btn_id: Int, posiciones: Array<Array<Char>>, posY: Int, posX: Int): Array<Array<Char>> {
        val btn: Button = activity.findViewById<Button>(btn_id)
        if (posiciones[posY][posX] == ' ') {
            when(posiciones.size) {
                3 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.o_3_3)
                6 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.o_6_6)
                9 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.o_9_9)
            }
        }
        return posiciones
    }
}