package mobilki.minesweeper

import android.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gameboard = findViewById<GridLayout>(R.id.gameboard)
        val gameBoardSize = 9
        gameboard.columnCount = gameBoardSize
        gameboard.rowCount = gameBoardSize

        for (i in 1..gameBoardSize*gameBoardSize){
            val boardcell = Button(gameboard.context)
            boardcell.text = i.toString()
            boardcell.gravity = Gravity.FILL
            boardcell.layoutParams = Layout
            gameboard.addView(boardcell)
        }
    }
}