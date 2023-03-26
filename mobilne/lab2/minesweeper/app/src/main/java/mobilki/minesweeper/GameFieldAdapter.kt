package mobilki.minesweeper

import android.graphics.Color.GRAY
import android.graphics.Color.LTGRAY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class GameFieldAdapter(private val dataSet: GameField, private val CellClickListener: OnCellClickListener) :
    RecyclerView.Adapter<GameFieldAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cell: TextView

        init {
            // Define click listener for the ViewHolder's View
            cell = view.findViewById(R.id.gamecell)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.game_cell, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.cell.setBackgroundColor(GRAY)

        if(dataSet.gameState == GameField.GameState.LOSE || dataSet.Board[position].isRevealed){
            viewHolder.cell.setBackgroundColor(LTGRAY)
            if (dataSet.Board[position].hasBomb){
                viewHolder.cell.text = "💣"
            } else if(dataSet.Board[position].proximityBombs > 0) {
                viewHolder.cell.text = dataSet.Board[position].proximityBombs.toString()
            }
        }else if(dataSet.Board[position].isFlagged){
            viewHolder.cell.text = "\uD83C\uDDF5\uD83C\uDDF1"
        }

        viewHolder.cell.setOnClickListener {
            CellClickListener.onCellClicked(position)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.Board.size

}
