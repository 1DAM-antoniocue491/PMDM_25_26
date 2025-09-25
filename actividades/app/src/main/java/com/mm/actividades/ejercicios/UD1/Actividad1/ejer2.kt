package com.mm.actividades.ejercicios.UD1.Actividad1

import java.lang.Exception

fun main() {
    val child = 5
    val adult = 200
    val senior = 89

    val isMonday = true

    println("The movie ticket price for a person aged $child is ${ticketPrice(child, isMonday)}€.")
    println("The movie ticket price for a person aged $adult is ${ticketPrice(adult, isMonday)}€.")
    println("The movie ticket price for a person aged $senior is ${ticketPrice(senior, isMonday)}€.")
}

fun ticketPrice(age: Int, isMonday: Boolean): Int {
    when (age) {
        in 0..12 -> return 15
        in 13..60 -> return 25
        in 61 .. 100 -> return 20
        else -> throw Exception("La edad del espectador no es válido")
    }
}