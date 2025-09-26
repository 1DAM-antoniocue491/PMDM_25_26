package com.mm.actividades.ejercicios.UD1.Actividad1

import android.provider.Settings
import java.lang.Exception

fun main() {
    val child = 5
    val adult = 45
    val senior = 89

    val isMonday = true

    result(child, isMonday)
    result(adult, isMonday)
    result(senior, isMonday)
}

fun result (age: Int, isMonday: Boolean) {
    val price : Int = ticketPrice(age, isMonday);

    if (price != -1)
        println("The movie ticket price for a person aged $age is ${price}€.")
    else
        System.err.println("La edad del espectador de $age años no es válida")
}

fun ticketPrice(age: Int, isMonday: Boolean): Int {
    when (age) {
        in 0..12 -> return 15
        in 13..60 -> {
            if (isMonday)
                return 5
            else
                return 30
        }
        in 61 .. 100 -> return 20
        else -> return -1
    }
}