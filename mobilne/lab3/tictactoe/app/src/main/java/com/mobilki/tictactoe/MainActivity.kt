package com.mobilki.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.mobilki.tictactoe.ui.theme.TictactoeTheme
import com.touchlane.gridpad.GridPad
import com.touchlane.gridpad.GridPadCells
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TictactoeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Gameboard()


                }
            }
        }
    }
}

class GameViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var gameEngine: GameEngine
    val initGameSize = 3
    private fun getGameUiState(): GameUiState {
        return GameUiState(
            gameEngine.getBoard(),
            gameEngine.currentTurn,
            gameEngine.gameSize,
            gameEngine.gameOverFlag
        )
    }

    fun processMove(index: Int) {
        gameEngine.processMove(index)
        _uiState.value = getGameUiState()
    }

    fun changeSize(size: Int) {
        var actualSize = size
        if(actualSize < 2) actualSize = 2
        if(actualSize > 20) actualSize = 20
        gameEngine.changeSize(actualSize)
        _uiState.value = getGameUiState()
    }

    fun startGame() {
        gameEngine.initGame()
        _uiState.value = getGameUiState()
    }

    init {
        gameEngine = GameEngine(initGameSize)
        _uiState.value = getGameUiState()
    }


}

data class GameUiState(
    val boardState: Array<GameEngine.fieldState>? = null,
    val playerTurn: GameEngine.playerTurn? = null,
    val boardSize: Int? = null,
    val isGameOver: Boolean? = null
)

@Preview
@Composable
fun Gameboard(gameViewModel: GameViewModel = viewModel()) {
    Column {
        val gameUiState by gameViewModel.uiState.collectAsState()
        val boardSize = gameUiState.boardSize!!
        Box(
            modifier = Modifier
                .aspectRatio(1.0f)
        ) {
            GridPad(
                cells = GridPadCells(rowCount = boardSize, columnCount = boardSize)
            ) {
                for (i in gameUiState.boardState!!.indices) {
                    item {
                        GameField(
                            fieldState = gameUiState.boardState!![i],
                            onFieldClick = { gameViewModel.processMove(i) })
                    }
                }
            }
        }
        Row(){
            if(gameUiState.isGameOver!=null && gameUiState.isGameOver!! ) {

                Button(modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color.Cyan) ,onClick = { gameViewModel.changeSize(gameUiState.boardSize!! - 1) }) {
                    Text("<")
                }
                Button(modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color.Cyan) ,onClick = { gameViewModel.startGame() }) {
                    Text("Start")
                }
                Button(modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color.Cyan) ,onClick = { gameViewModel.changeSize(gameUiState.boardSize!! + 1) }) {
                    Text(">")
                }
            }
        }

    }


}


@Composable
fun GameField(fieldState: GameEngine.fieldState, onFieldClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1.0f)
            .border(
                BorderStroke(
                    2.dp,
                    if (fieldState == GameEngine.fieldState.winningCross ||
                        fieldState == GameEngine.fieldState.winningDot
                    ) SolidColor(
                        Color.Cyan
                    )
                    else SolidColor(Color.Black)
                )
            )
            .clickable {
                onFieldClick()
            }
    ) {
        when (fieldState) {
            GameEngine.fieldState.dot, GameEngine.fieldState.winningDot -> {
                Image(painter = painterResource(id = R.drawable.circle),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onFieldClick() }
                        .fillMaxSize()
                )
            }
            GameEngine.fieldState.cross, GameEngine.fieldState.winningCross ->
                Image(painter = painterResource(id = R.drawable.cross),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onFieldClick() }
                        .fillMaxSize()
                )
            else -> {}
        }
    }
}


