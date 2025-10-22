package com.example.pmdm_t2_tresenraya.model

class IA {

    private val aiChar = 'O'
    private val playerChar = 'X'

    // --- Verifica si alguien ganó ---
    fun checkWin(table: Array<Array<Char>>): Boolean {
        val size = table.size

        // Filas
        for (row in 0 until size) {
            if (table[row].all { it == table[row][0] && it != ' ' }) return true
        }

        // Columnas
        for (col in 0 until size) {
            if ((0 until size).all { row -> table[row][col] == table[0][col] && table[row][col] != ' ' }) return true
        }

        // Diagonal principal
        if ((0 until size).all { i -> table[i][i] == table[0][0] && table[i][i] != ' ' }) return true

        // Diagonal secundaria
        if ((0 until size).all { i -> table[i][size - 1 - i] == table[0][size - 1] && table[i][size - 1 - i] != ' ' }) return true

        return false
    }

    // --- Encuentra la mejor posición para la IA ---
    fun bestPosition(board: Array<Array<Char>>): Pair<Int, Int> {
        val size = board.size
        var bestScore = Int.MIN_VALUE
        var bestMove = Pair(0, 0)

        for (row in 0 until size) {
            for (col in 0 until size) {
                if (board[row][col] == ' ') {
                    val score = evaluateMove(board, row, col)
                    if (score > bestScore) {
                        bestScore = score
                        bestMove = Pair(row, col)
                    }
                }
            }
        }
        return bestMove
    }

    // --- Evalúa una jugada en fila, columna y diagonales ---
    private fun evaluateMove(board: Array<Array<Char>>, row: Int, col: Int): Int {
        board[row][col] = aiChar
        var score = 0

        // Filas y columnas
        val size = board.size
        val fila = board[row].toList()
        val columna = (0 until size).map { r -> board[r][col] }

        // Diagonales
        val diagPrincipal = if (row == col) (0 until size).map { i -> board[i][i] } else emptyList()
        val diagSecundaria = if (row + col == size - 1) (0 until size).map { i -> board[i][size - 1 - i] } else emptyList()

        // Evaluar cada línea
        score += evaluateLine(fila)
        score += evaluateLine(columna)
        if (diagPrincipal.isNotEmpty()) score += evaluateLine(diagPrincipal)
        if (diagSecundaria.isNotEmpty()) score += evaluateLine(diagSecundaria)

        board[row][col] = ' '
        return score
    }

    private fun evaluateLine(line: List<Char>): Int {
        val aiCount = line.count { it == aiChar }
        val playerCount = line.count { it == playerChar }
        val emptyCount = line.count { it == ' ' }

        // Prioridad: ganar > bloquear > preparar
        return when {
            aiCount == 3 -> 100  // IA gana
            playerCount == 3 -> 90 // Bloquear jugador
            aiCount == 2 && emptyCount == 1 -> 10
            playerCount == 2 && emptyCount == 1 -> 50
            else -> 0
        }
    }
}
