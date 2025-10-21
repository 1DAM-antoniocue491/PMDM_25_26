package com.example.pmdm_t2_tresenraya.model

import kotlin.collections.mutableListOf

class ia () {
    internal var player = 'x'
    internal var opponent = 'o'

    internal inner class Move(var row: Int, var col: Int)

    internal fun isMovesLeft(board: Array<Array<Char>>): Boolean {
        for (i in 0..board.size-1)
            for (j in 0..board.size-1)
                if (board[i][j] == '_')
                    return true
        return false
    }

    fun checkWin(table: Array<Array<Char>>): Boolean {
        var list: MutableList<Char> = mutableListOf()
        for (x in table.indices) {
            for (y in table[x].indices) {
                list.add(table[x][y])
            }
            if (list.toSet().size == 1) return true
            list.clear()
        }
        for (x in table.indices) {
            for (y in table[x].indices) {
                list.add(table[y][x])
            }
            if (list.toSet().size == 1) return true
            list.clear()
        }
        var x = 0
        var y = 0
        for (a in 0..table.size-1) {
            list.add(table[x][y])
            x++
            y++
        }
        if (list.toSet().size == 1) return true
        list.clear()
        var b = list.size-1
        for (a in 0..list.size-1) {
            list.add(table[b][a])
            b--
        }
        if (list.toSet().size == 1) return true
        return false
    }

    fun evaluacion(board: Array<Array<Char>>, symbol: Char): Int {
        var score = 0;

        // Check for potential wins or blocks for the player
        for (row in 0 until board.size) {
            val rowOccupiedByPlayer = (0 until board.size).all { board[row][it] == player }
            if (rowOccupiedByPlayer) {
                score -= 100 // Player has a winning possibility
            }

            val rowOccupiedByOpponent = (0 until board.size).all { board[row][it] == opponent }
            if (rowOccupiedByOpponent) {
                score += 100 // AI has a winning possibility
            }
        }

        for (col in 0 until board.size) {
            val colOccupiedByPlayer = (0 until board.size).all { board[it][col] == player }
            if (colOccupiedByPlayer) {
                score -= 100 // Player has a winning possibility
            }

            val colOccupiedByOpponent = (0 until board.size).all { board[it][col] == opponent }
            if (colOccupiedByOpponent) {
                score += 100 // AI has a winning possibility
            }
        }

        val mainDiagonalOccupiedByPlayer = (0 until board.size).all { board[it][it] == player }
        if (mainDiagonalOccupiedByPlayer) {
            score -= 100 // Player has a winning possibility
        }

        val mainDiagonalOccupiedByOpponent = (0 until board.size).all { board[it][it] == opponent }
        if (mainDiagonalOccupiedByOpponent) {
            score += 100 // AI has a winning possibility
        }

        val antiDiagonalOccupiedByPlayer = (0 until board.size).all { board[it][2 - it] == player }
        if (antiDiagonalOccupiedByPlayer) {
            score -= 100 // Player has a winning possibility
        }

        val antiDiagonalOccupiedByOpponent = (0 until board.size).all { board[it][2 - it] == opponent }
        if (antiDiagonalOccupiedByOpponent) {
            score += 100 // AI has a winning possibility
        }

        // Additional evaluation factors
        // Center control
        if (board.size == 3) {
            if (board[1][1] == symbol) {
                score += 10
            }
        } else {
            if (board[board.size/2][board.size/2] == symbol) {
                score += 10
            }
        }
        // Corner control
        if (board[0][0] == symbol || board[0][board.size-1] == symbol || board[board.size-1][0] == symbol || board[board.size-1][board.size-1] == symbol) {
            score += 5
        }

        return score
    }

    // Minimax algorithm implementation
    internal fun minimax(board: Array<Array<Char>>, depth: Int, isMax: Boolean): Int {
        val score = evaluacion(board, player)

        if (score == 100 || score == -100)
            return score

        if (!isMovesLeft(board) || depth == 4) // Increased depth for deeper search
            return 0

        var bestVal = if (isMax) Int.MIN_VALUE else Int.MAX_VALUE

        for (i in 0 until board.size-1) {
            for (j in 0 until board.size-1) {
                if (board[i][j] == '_') {
                    if (isMax) {
                        board[i][j] = player
                        bestVal = maxOf(bestVal, minimax(board, depth + 1, !isMax))
                    } else {
                        board[i][j] = opponent
                        bestVal = minOf(bestVal, minimax(board, depth + 1, !isMax))
                    }
                    board[i][j] = '_'
                }
            }
        }

        return bestVal
    }

    internal fun findBestMove(board: Array<Array<Char>>): Int {
        // Check if AI has a winning possibility and prioritize it
        for (i in 0 until board.size-1) {
            for (j in 0 until board.size-1) {
                if (board[i][j] == '_') {
                    board[i][j] = opponent
                    if (evaluacion(board, opponent) == 100) {
                        board[i][j] = '_'
                        return i * board.size-1 + j + 1
                    }
                    board[i][j] = '_'
                }
            }
        }

        // If AI doesn't have a winning possibility, defend against the player
        for (i in 0 until board.size-1) {
            for (j in 0 until board.size-1) {
                if (board[i][j] == '_') {
                    board[i][j] = player
                    if (evaluacion(board, player) == -100) {
                        board[i][j] = '_'
                        return i * board.size-1 + j + 1
                    }
                    board[i][j] = '_'
                }
            }
        }

        // If neither player nor AI has a high chance to win, find the best move for the AI
        var bestVal = Int.MIN_VALUE
        var bestMove = -1

        for (i in 0 until board.size-1) {
            for (j in 0 until board.size-1) {
                if (board[i][j] == '_') {
                    board[i][j] = player
                    val moveVal = minimax(board, 0, false)
                    board[i][j] = '_'
                    if (moveVal > bestVal) {
                        bestVal = moveVal
                        bestMove = i * board.size-1 + j + 1
                    }
                }
            }
        }

        return bestMove
    }
}