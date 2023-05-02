package com.mobilki.tictactoe

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mobilki.tictactoe.ui.theme.TictactoeTheme
import com.touchlane.gridpad.GridPad
import com.touchlane.gridpad.GridPadCells

class MainActivity : ComponentActivity() {

    val GAME_SIZE = 3;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TictactoeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                }
            }
        }
    }
}




class GameEngine(gameSize: Int){
    enum class fieldState { cross, dot, empty }
}

@Composable
fun Gameboard(boardSize: Int, boardFields: List<GameEngine.fieldState>) {
    Box(modifier = Modifier.aspectRatio(1.0f)) {
        GridPad(
            cells = GridPadCells(rowCount = boardSize, columnCount = boardSize)
        ){
            for (a in boardFields){
                item{ GameField(fieldState = a)}
            }
        }
    }

}

@Composable
fun GameField(fieldState: GameEngine.fieldState) {
    Box(contentAlignment = Alignment.Center,
    modifier = Modifier.aspectRatio(1.0f)) {
        when (fieldState) {
            com.mobilki.tictactoe.GameEngine.fieldState.dot -> {
                Image(painter = painterResource(id = R.drawable.circle),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { println("Dot Clicked!") }
                        .fillMaxSize()
                )
            }
            com.mobilki.tictactoe.GameEngine.fieldState.cross ->
                Image(painter = painterResource(id = R.drawable.cross),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { println("Cross Clicked!") }
                        .fillMaxSize()
                )
            else -> {}
        }
    }
}


@Preview
@Composable
fun emptyGameField() {
    GameField(fieldState = GameEngine.fieldState.empty)
}

@Preview
@Composable
fun gameBoard() {
    Gameboard(
        3, listOf(
            GameEngine.fieldState.empty, GameEngine.fieldState.dot, GameEngine.fieldState.cross,
            GameEngine.fieldState.dot, GameEngine.fieldState.cross, GameEngine.fieldState.cross,
            GameEngine.fieldState.dot, GameEngine.fieldState.dot, GameEngine.fieldState.empty
        )
    )
}
