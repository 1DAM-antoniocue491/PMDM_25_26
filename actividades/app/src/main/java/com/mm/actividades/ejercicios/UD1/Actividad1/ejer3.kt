package com.mm.actividades.ejercicios.UD1.Actividad1

import java.util.Scanner

fun main() {
    print("Indica la operación a realizar con el siguiente formato (C -> F 4.5): ")
    val usuario : List<String> = readln().split(" ")

    var result: Double
    var number: Double = usuario.get(1).toDouble()

    when (usuario.get(0)) {
        in "C" -> {
            when (usuario.get(2)) {
                in "F" -> result = C_F(number)
                in "K" -> result = C_K(number)
            }
        }
        in "K" -> {

        }
    }
}

fun C_F(number: Double): Double {
    return ((number*1.8)+32)
}

fun C_K(number: Double): Double {
    return number+273.15
}

fun K_C(number: Double): Double {
    return number-273.15
}

fun K_F(number: Double): Double {
    return ((number-273.15)*1.8+32)
}

fun F_K(number: Double): Double {
    return ((number+459.67)*(5/9))
}

fun F_C(number: Double): Double {
    return (number - 32) * 1.8
}

/*
Esto lo omito porqué no entiendo cómo es el tratado de los datos.

fun printFinalTemperature(
    initialMeasurement: Double,
    initialUnit: String,
    finalUnit: String,
    conversionFormula: (Double) -> Double
) {
    val finalMeasurement = String.format("%.2f", conversionFormula(initialMeasurement)) // two decimal places
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}
 */