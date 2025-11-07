package com.example.pmdm_t2_tresenraya.model

enum class Difficulty { EASY, MEDIUM, HARD, VOID }

class IA(
    private val board: Array<CellState>,
    private val playerSymbol: CellState,
    private val aiSymbol: CellState,
    private val isFirstMove: Boolean
) {
    companion object {
        private const val WIN_SCORE = 10
        private const val LOSE_SCORE = -10
    }

    fun getMove(difficulty: Difficulty?): Int {
        return when (difficulty) {
            Difficulty.EASY -> getEasyMove()
            Difficulty.MEDIUM -> getMediumMove()
            Difficulty.HARD -> getHardMove()
            Difficulty.VOID -> getEasyMove()
            null -> getEasyMove()
        }
    }

    private fun getEasyMove(): Int {
        val emptyIndices = board.indices.filter { board[it] == CellState.CLEAR }
        return if (emptyIndices.isNotEmpty()) emptyIndices.random() else -1
    }

    private fun getMediumMove(): Int {
        // Intentar ganar
        for (i in board.indices) {
            if (board[i] == CellState.CLEAR) {
                board[i] = aiSymbol
                if (checkWinner() == aiSymbol) {
                    board[i] = CellState.CLEAR
                    return i
                }
                board[i] = CellState.CLEAR
            }
        }

        // Bloquear al jugador
        for (i in board.indices) {
            if (board[i] == CellState.CLEAR) {
                board[i] = playerSymbol
                if (checkWinner() == playerSymbol) {
                    board[i] = CellState.CLEAR
                    return i
                }
                board[i] = CellState.CLEAR
            }
        }

        return getEasyMove()
    }

    private fun getHardMove(): Int {
        if (isFirstMove) {
            return 4 // Centro
        }

        var bestVal = Int.MIN_VALUE
        var bestMove = -1

        for (i in board.indices) {
            if (board[i] == CellState.CLEAR) {
                board[i] = aiSymbol
                val moveVal = minimax(0, false)
                board[i] = CellState.CLEAR

                if (moveVal > bestVal) {
                    bestVal = moveVal
                    bestMove = i
                }
            }
        }

        return bestMove
    }

    private fun minimax(depth: Int, isMaximizing: Boolean): Int {
        val winner = checkWinner()
        if (winner == aiSymbol) return WIN_SCORE - depth
        if (winner == playerSymbol) return LOSE_SCORE + depth
        if (isBoardFull()) return 0

        return if (isMaximizing) {
            var best = Int.MIN_VALUE
            for (i in board.indices) {
                if (board[i] == CellState.CLEAR) {
                    board[i] = aiSymbol
                    best = maxOf(best, minimax(depth + 1, false))
                    board[i] = CellState.CLEAR
                }
            }
            best
        } else {
            var best = Int.MAX_VALUE
            for (i in board.indices) {
                if (board[i] == CellState.CLEAR) {
                    board[i] = playerSymbol
                    best = minOf(best, minimax(depth + 1, true))
                    board[i] = CellState.CLEAR
                }
            }
            best
        }
    }

    private fun checkWinner(): CellState? {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // filas
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // columnas
            listOf(0, 4, 8), listOf(2, 4, 6)                   // diagonales
        )

        for (pattern in winPatterns) {
            val a = board[pattern[0]]
            val b = board[pattern[1]]
            val c = board[pattern[2]]
            if (a != CellState.CLEAR && a == b && b == c) {
                return a
            }
        }
        return null
    }

    private fun isBoardFull(): Boolean = board.all { it != CellState.CLEAR }




}