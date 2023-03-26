package mobilki.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),  OnCellClickListener{
    lateinit var game : GameField
    lateinit var gameboard : RecyclerView
    val gameBoardSize = 9
    val mines = 10
    var FLAG_MODE = false
    var RESTART_MODE = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameboard = findViewById<RecyclerView>(R.id.gameboard)
        gameboard.layoutManager = GridLayoutManager(this,gameBoardSize)
        game = GameField(gameBoardSize, mines)
        gameboard.adapter = GameFieldAdapter(game,this)
        setClickListeners()
    }

    private fun setClickListeners(){
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener() {
            if(!RESTART_MODE){
                if(FLAG_MODE){
                    button.text = getString(R.string.toogle_reveal)
                }else{
                    button.text = getString(R.string.toogle_flag)
                }
                FLAG_MODE = !FLAG_MODE
            } else{
                restartGame()
            }
        }
    }

    private fun restartGame(){
        game = GameField(gameBoardSize, mines)
        gameboard.adapter = GameFieldAdapter(game,this)
        FLAG_MODE = false
        RESTART_MODE = false
        findViewById<Button>(R.id.button).text  = getString(R.string.toogle_flag)
        findViewById<TextView>(R.id.gameEndMsg).visibility = View.INVISIBLE
    }
    override fun onCellClicked(gameCellId: Int) {
        if(FLAG_MODE){
            game.handleFlag(gameCellId)
        }else{
            game.handleReveal(gameCellId)
        }
        gameboard.adapter = GameFieldAdapter(game,this)
        if(game.gameState == GameField.GameState.WIN || game.gameState == GameField.GameState.LOSE){
            handle_game_end()
        }
    }

    private fun handle_game_end(){
        val gameEndText = findViewById<TextView>(R.id.gameEndMsg)
        RESTART_MODE = true
        findViewById<Button>(R.id.button).text  = getString(R.string.restart)
        if(game.gameState == GameField.GameState.WIN){
            gameEndText.text = getString(R.string.win_msg)
        }else{
            gameEndText.text = getString(R.string.lose_msg)
        }
        gameEndText.visibility = TextView.VISIBLE
    }

}