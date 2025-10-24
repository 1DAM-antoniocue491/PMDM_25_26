package com.example.pmdm_t2_tresenraya.model

import android.util.Log
import kotlin.collections.indices

class IA_plus (
    private val searchBlockSize: Int // tamaño de bloque a analizar (3, 4, 5)
) {
    private val aiChar = CellState.CIRCLE
    private val playerChar = CellState.CROSS

    fun checkWin(table: Array<Array<CellState>>): Boolean {
        for (row in table.indices) {
            for (col in table[row].indices) {
                if ((row + searchBlockSize) <= table.size && (col + searchBlockSize) <= table.size) {
                    val matriz = Array(searchBlockSize) { Array(searchBlockSize) { CellState.CLEAR } }
                    Array(searchBlockSize) { i ->
                        Array(searchBlockSize) { j ->
                            matriz[i][j] = table[row + i][col + j]
                        }
                    }
                    if (checkWinPlus(matriz)) return true
                }
            }
        }
        return false
    }

    // --- Verifica si alguien ganó ---
    private fun checkWinPlus(table: Array<Array<CellState>>): Boolean {
        val size = table.size

        // Filas
        for (row in 0 until size) {
            if (table[row].all { it == table[row][0] && it != CellState.CLEAR }) return true
        }

        // Columnas
        for (col in 0 until size) {
            if ((0 until size).all { row -> table[row][col] == table[0][col] && table[row][col] != CellState.CLEAR }) return true
        }

        // Diagonal principal
        if ((0 until size).all { i -> table[i][i] == table[0][0] && table[i][i] != CellState.CLEAR }) return true

        // Diagonal secundaria
        if ((0 until size).all { i -> table[i][size - 1 - i] == table[0][size - 1] && table[i][size - 1 - i] != CellState.CLEAR }) return true

        return false
    }

    // --- Encuentra la mejor posición para la IA ---
    fun bestPosition(table: Array<Array<CellState>>): Pair<Int, Int> {
        val bestMoves = mutableListOf<Pair<Int, Pair<Int, Int>>>()

        for (row in table.indices) {
            for (col in table[row].indices) {
                if (row <= table.size - searchBlockSize && col <= table[row].size - searchBlockSize) {
                    val matriz = Array(searchBlockSize) { Array(searchBlockSize) { CellState.CLEAR } }
                    for (i in 0 until searchBlockSize) {
                        for (j in 0 until searchBlockSize) {
                            matriz[i][j] = table[row + i][col + j]
                        }
                    }
                    val position = bestPositionPlus(matriz)
                    val absoluteMove = Pair(row + position.second.first, col + position.second.second)
                    bestMoves.add(Pair(position.first, absoluteMove))
                }
            }
        }

        val bestAbsoluteMove = bestMoves.maxByOrNull { it.first }?.second ?: Pair(0, 0)


        Log.i("Prueba", bestAbsoluteMove.toString())

        return bestAbsoluteMove
    }

    // --- Encuentra la mejor posición dentro de una submatriz ---
    private fun bestPositionPlus(board: Array<Array<CellState>>): Pair<Int, Pair<Int, Int>> {
        val size = board.size
        var bestScore = Int.MIN_VALUE
        var bestMove = Pair(0, 0)

        for (row in 0 until size) {
            for (col in 0 until size) {
                if (board[row][col] == CellState.CLEAR) {
                    val score = evaluateMove(board, row, col)
                    if (score > bestScore) {
                        bestScore = score
                        bestMove = Pair(row, col)
                    }
                }
            }
        }

        Log.i("AI", "Mejor movimiento local: $bestMove (score=$bestScore)")
        return Pair(bestScore, bestMove)
    }


    // --- Evalúa una jugada en fila, columna y diagonales ---
    private fun evaluateMove(board: Array<Array<CellState>>, row: Int, col: Int): Int {
        board[row][col] = aiChar
        var score = 0
        val size = board.size

        // --- Fila y columna ---
        val fila = board[row].toList()
        val columna = (0 until size).map { r -> board[r][col] }

        // --- Diagonal principal ---
        val diagPrincipal = mutableListOf<CellState>()
        var r = row
        var c = col
        while (r > 0 && c > 0) { r--; c-- }
        while (r < size && c < size) {
            diagPrincipal.add(board[r][c])
            r++; c++
        }

        // --- Diagonal secundaria ---
        val diagSecundaria = mutableListOf<CellState>()
        r = row
        c = col
        while (r > 0 && c < size - 1) { r--; c++ }
        while (r < size && c >= 0) {
            diagSecundaria.add(board[r][c])
            r++; c--
        }

        // --- Evaluar todas las líneas ---
        score += evaluateLine(fila)
        score += evaluateLine(columna)
        score += evaluateLine(diagPrincipal)
        score += evaluateLine(diagSecundaria)

        // Pequeña bonificación por ocupar el centro
        if (row == size / 2 && col == size / 2) score += 25

        board[row][col] = CellState.CLEAR
        return score
    }


    // --- Evalúa una línea (fila, columna o diagonal) ---
    private fun evaluateLine(line: List<CellState>): Int {
        val target = searchBlockSize // 3, 4 o 5
        var score = 0

        fun sequenceScore(count: Int, openEnds: Int, isAI: Boolean): Int {
            // Base según número de fichas consecutivas
            val base = when (count) {
                target -> 10_000          // victoria directa IA
                target - 1 -> 1_000       // a una jugada de ganar IA
                target - 2 -> 300
                target - 3 -> 100
                else -> 10
            }

            // Bonificación por tener espacios abiertos a los lados
            val opennessBonus = when (openEnds) {
                2 -> 2.0
                1 -> 1.0
                else -> 0.2
            }

            // Si es amenaza del jugador inminente (target-1), darle más peso
            val adjustedBase = if (!isAI && count == target - 1) 1_500 else base

            return (adjustedBase * opennessBonus).toInt()
        }


        // Analizar secuencias consecutivas
        var i = 0
        while (i < line.size) {
            if (line[i] == CellState.CLEAR) {
                i++
                continue
            }

            val current = line[i]
            var count = 1
            var j = i + 1
            while (j < line.size && line[j] == current) {
                count++
                j++
            }

            // Chequear si la secuencia tiene huecos libres a los lados
            val leftOpen = (i - 1 >= 0 && line[i - 1] == CellState.CLEAR)
            val rightOpen = (j < line.size && line[j] == CellState.CLEAR)
            val openEnds = listOf(leftOpen, rightOpen).count { it }

            if (current == aiChar) {
                score += sequenceScore(count, openEnds, isAI = true)
            } else if (current == playerChar) {
                score += sequenceScore(count, openEnds, isAI = false)
            }

            i = j
        }

        return score
    }


}