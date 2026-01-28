package com.example.notasapp

data class DialogOption(
    val title: String,
    val action: () -> Unit
)