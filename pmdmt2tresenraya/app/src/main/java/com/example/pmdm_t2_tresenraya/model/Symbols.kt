package com.example.pmdm_t2_tresenraya.model

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import androidx.core.content.ContextCompat
import com.example.pmdm_t2_tresenraya.R

class Symbols (context: Context){
    val play = Play(context as Activity)

    fun XSymbol(context: Context): ShapeDrawable {
        val color = play.prefs.app.getStyle(context)

        val shape = object : Shape() {
            override fun draw(canvas: Canvas, paint: Paint) {
                val ancho = canvas.width.toFloat()
                val alto = canvas.height.toFloat()

                paint.color = color
                paint.strokeWidth = ancho / 8
                paint.style = Paint.Style.STROKE
                paint.isAntiAlias = true

                // Dibuja la X
                canvas.drawLine(0f, 0f, ancho, alto, paint)
                canvas.drawLine(ancho, 0f, 0f, alto, paint)
            }
        }

        return ShapeDrawable(shape)
    }

    /**
     * Drawable para la "O"
     */
    fun OSymbol(context: Context): ShapeDrawable {
        val prefs = context.getSharedPreferences("PreferenciasJuego", Context.MODE_PRIVATE)
        val color = prefs.getInt(
            "color_o",
            ContextCompat.getColor(context, R.color.blue)
        )

        val shape = object : Shape() {
            override fun draw(canvas: Canvas, paint: Paint) {
                val ancho = canvas.width.toFloat()
                val alto = canvas.height.toFloat()
                val radio = ancho.coerceAtMost(alto) / 2.5f

                paint.color = color
                paint.strokeWidth = ancho / 8
                paint.style = Paint.Style.STROKE
                paint.isAntiAlias = true

                // Dibuja la O
                canvas.drawCircle(ancho / 2, alto / 2, radio, paint)
            }
        }

        return ShapeDrawable(shape)
    }
}