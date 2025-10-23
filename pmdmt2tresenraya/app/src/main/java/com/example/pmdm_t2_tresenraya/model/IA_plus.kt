package com.example.pmdm_t2_tresenraya.model

import android.content.Context
import android.util.Log

class IA_plus(
    private val context: Context,
    private val winCondition: Int,  // nº de fichas para ganar (3, 4, 5)
    private val searchBlockSize: Int // tamaño de bloque a analizar (3, 4, 5)
) {
    private val aiChar = CellState.CIRCLE
    private val playerChar = CellState.CROSS

    // --- Verifica si alguien ganó ---
    fun checkWin(table: Array<Array<CellState>>): Boolean {
        val size = table.size

        // Recorre bloques del tamaño definido
        for (blockRow in 0..size - searchBlockSize) {
            for (blockCol in 0..size - searchBlockSize) {
                // Revisa ese bloque concreto
                if (checkBlock(table, blockRow, blockCol)) {
                    return true
                }
            }
        }
        return false
    }

    private fun checkBlock(table: Array<Array<CellState>>, startRow: Int, startCol: Int): Boolean {
        // Límite del bloque
        val endRow = startRow + searchBlockSize
        val endCol = startCol + searchBlockSize

        // Dentro del bloque, revisa direcciones
        for (row in startRow until endRow) {
            for (col in startCol until endCol) {
                if (checkDirection(table, row, col, 1, 0)) return true  // horizontal
                if (checkDirection(table, row, col, 0, 1)) return true  // vertical
                if (checkDirection(table, row, col, 1, 1)) return true  // diagonal principal
                if (checkDirection(table, row, col, 1, -1)) return true // diagonal secundaria
            }
        }
        return false
    }

    private fun checkDirection(
        table: Array<Array<CellState>>,
        row: Int,
        col: Int,
        dRow: Int,
        dCol: Int
    ): Boolean {
        val size = table.size
        val start = table[row][col]
        if (start == CellState.CLEAR) return false

        // Verifica n fichas seguidas (winCondition)
        for (i in 1 until winCondition) {
            val r = row + dRow * i
            val c = col + dCol * i
            // Si salimos del tablero o del bloque → no hay línea
            if (r !in 0 until size || c !in 0 until size) return false
            if (table[r][c] != start) return false
        }
        return true
    }

    // --- Encuentra la mejor posición para la IA ---
    fun bestPosition(board: Array<Array<CellState>>): Pair<Int, Int> {
        val size = board.size
        var bestScore = Int.MIN_VALUE
        var bestMove = Pair(0, 0)

        for (row in 0 until size) {
            for (col in 0 until size) {
                if (board[row][col] == CellState.CLEAR) {
                    val score = evaluateMove(board, row, col)
                    Log.i("IA_plus", "Mejor jugada: $row $col (Score: $score)")
                    if (score > bestScore) {
                        bestScore = score
                        bestMove = Pair(row, col)

                    }
                }
            }
        }


        return bestMove
    }

    // --- Evalúa una jugada ---
    private fun evaluateMove(board: Array<Array<CellState>>, row: Int, col: Int): Int {
        board[row][col] = aiChar
        var score = 0
        val size = board.size

        // Solo evalúa un área limitada (bloque de análisis)
        val rowStart = maxOf(0, row - searchBlockSize / 2)
        val rowEnd = minOf(size, row + searchBlockSize / 2 + 1)
        val colStart = maxOf(0, col - searchBlockSize / 2)
        val colEnd = minOf(size, col + searchBlockSize / 2 + 1)

        for (r in rowStart until rowEnd) {
            for (c in colStart until colEnd) {
                score += evaluateLinesAround(board, r, c)
            }
        }

        board[row][col] = CellState.CLEAR
        return score
    }

    private fun evaluateLinesAround(board: Array<Array<CellState>>, row: Int, col: Int): Int {
        val directions = listOf(
            Pair(1, 0), Pair(0, 1),
            Pair(1, 1), Pair(1, -1)
        )
        var totalScore = 0
        for ((dr, dc) in directions) {
            val line = mutableListOf<CellState>()
            for (i in -winCondition + 1 until winCondition) {
                val r = row + dr * i
                val c = col + dc * i
                if (r in board.indices && c in board.indices) {
                    line.add(board[r][c])
                }
            }
            totalScore += evaluateLine(line)
        }
        return totalScore
    }

    private fun evaluateLine(line: List<CellState>): Int {
        val aiCount = line.count { it == aiChar }
        val playerCount = line.count { it == playerChar }

        return when {
            aiCount >= winCondition -> 1000 // gana
            playerCount >= winCondition - 1 -> 900 // bloquear
            aiCount == winCondition - 1 -> 100
            aiCount >= 1 -> 10
            else -> 0
        }
    }
}
