package com.example.pmdm_t2_tresenraya.model

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.pmdm_t2_tresenraya.R
import android.graphics.Color
import androidx.core.graphics.toColorInt

class Symbols (){

    fun XSymbol(): ShapeDrawable {

        val shape = object : Shape() {
            @SuppressLint("ResourceAsColor")
            override fun draw(canvas: Canvas, paint: Paint) {
                val ancho = canvas.width.toFloat()
                val alto = canvas.height.toFloat()

                paint.color = R.color.red
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
    fun OSymbol(): ShapeDrawable {
        val shape = object : Shape() {
            override fun draw(canvas: Canvas, paint: Paint) {
                val ancho = canvas.width.toFloat()
                val alto = canvas.height.toFloat()
                val radio = ancho.coerceAtMost(alto) / 2.5f

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