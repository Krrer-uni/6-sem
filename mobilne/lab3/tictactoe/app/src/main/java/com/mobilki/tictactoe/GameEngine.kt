package com.mobilki.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class GameEngine(var gameSize: Int) {
    enum class fieldState { cross, dot, empty, winningCross, winningDot }
    enum class playerTurn { dotPlayer, crossPlayer }

    var currentTurn : playerTurn by mutableStateOf( playerTurn.dotPlayer)
    var boardState = Array(gameSize * gameSize) { fieldState.empty }
    var gameOverFlag = true
    var noMoves = 0

    fun initGame() {
        boardState = Array(gameSize * gameSize) { fieldState.empty }
        currentTurn = playerTurn.dotPlayer
        noMoves = 0
        gameOverFlag = false
    }

    fun changeSize(newGamesize: Int) {
        this.gameSize = newGamesize
        boardState = Array(gameSize * gameSize) { fieldState.empty }

    }

    fun getBoard(): Array<fieldState> {
        return boardState
    }

    fun processMove(fieldIndex: Int) {

        if(gameOverFlag) {
//            initGame()
            return
        }
        if (boardState[fieldIndex] != fieldState.empty) {
            return
        } else {
            if (currentTurn == playerTurn.dotPlayer) {
                boardState[fieldIndex] = fieldState.dot
                currentTurn = playerTurn.crossPlayer
            } else {
                boardState[fieldIndex] = fieldState.cross
                currentTurn = playerTurn.dotPlayer
            }
        }
        noMoves++
        checkWin()
        if(noMoves == gameSize*gameSize){
            gameOverFlag = true
        }
    }

    private fun checkWin() : Boolean {
        var acc = 0;
        for (i in 0 until gameSize) {
            for (j in 0 until gameSize) {
                val field = boardState[gameSize * i + j]
                if (field == fieldState.dot) {
                    acc++;
                } else if (field == fieldState.cross) {
                    acc--;
                }
            }
            if (acc == gameSize) {
                for (j in 0 until gameSize) {
                    boardState[gameSize * i + j] = fieldState.winningDot
                }
                gameOver(playerTurn.dotPlayer)
                return true
            } else if (acc == -gameSize) {
                for (j in 0 until gameSize) {
                    boardState[gameSize * i + j] = fieldState.winningCross
                }
                gameOver(playerTurn.crossPlayer)
                return true
            }
            acc = 0;
        }
        for (i in 0 until gameSize) {
            for (j in 0 until gameSize) {
                val field = boardState[gameSize * j + i]
                if (field == fieldState.dot) {
                    acc++;
                } else if (field == fieldState.cross) {
                    acc--;
                }
            }
            if (acc == gameSize) {
                for (j in 0 until gameSize) {
                    boardState[gameSize * j + i] = fieldState.winningDot
                }
                gameOver(playerTurn.dotPlayer)
                return true
            } else if (acc == -gameSize) {
                for (j in 0 until gameSize) {
                    boardState[gameSize * j + i] = fieldState.winningCross
                }
                gameOver(playerTurn.crossPlayer)
                return true
            }
            acc = 0;
        }
        return false
    }

    private fun gameOver(playerWon: playerTurn) {
        gameOverFlag = true
//        initGame()
        println("gameOver")
    }
}
