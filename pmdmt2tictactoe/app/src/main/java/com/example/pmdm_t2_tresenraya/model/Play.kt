package com.example.pmdm_t2_tresenraya.model

import android.app.Activity
import android.util.TypedValue
import android.widget.Button
import com.google.android.material.R

class Play (val activity: Activity) {

    fun setBaseColor(celda: Button?) {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
        val colorFromTheme = typedValue.data

        celda?.setBackgroundColor(colorFromTheme)
    }

    fun setX(btn_id: Int, posiciones: Array<Array<CellState>>, posX: Int, posY: Int) {
        val btn: Button = activity.findViewById<Button>(btn_id)
        when(posiciones.size) {
            3 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.x_3_3)
            6 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.x_6_6)
            9 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.x_9_9)
        }

    }

    fun setO(btn_id: Int, posiciones: Array<Array<CellState>>, posY: Int, posX: Int) {
        val btn: Button = activity.findViewById<Button>(btn_id)
        when(posiciones.size) {
            3 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.o_3_3)
            6 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.o_6_6)
            9 -> btn.setBackgroundResource(com.example.pmdm_t2_tresenraya.R.drawable.o_9_9)
        }
    }
}