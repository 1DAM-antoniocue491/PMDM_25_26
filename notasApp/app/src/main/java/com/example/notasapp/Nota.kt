package com.example.notasapp

data class Nota(
    val id: String,
    val titulo: String,
    val contenido: String,
    val prioridad: Prioridad,
    val fechaCreacion: Long
)
