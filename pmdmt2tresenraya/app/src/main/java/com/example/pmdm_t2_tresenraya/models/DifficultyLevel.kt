package com.jaennova.tictactoe.models

enum class DifficultyLevel {
    EASY,
    MEDIUM,
    HARD
}

// Lista de niveles de dificultad
val difficultyLevels = listOf(
    DifficultyLevel.EASY,
    DifficultyLevel.MEDIUM,
    DifficultyLevel.HARD
)

enum class Player {
    X,
    O
}