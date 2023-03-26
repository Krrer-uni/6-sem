package mobilki.minesweeper

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class GameCell()  {
    var isRevealed : Boolean = false
    var isFlagged : Boolean = false
    var hasBomb : Boolean = false
    var proximityBombs : Int = 0
}