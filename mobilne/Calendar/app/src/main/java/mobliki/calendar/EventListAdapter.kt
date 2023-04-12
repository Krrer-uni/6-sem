package mobliki.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventListAdapter(private val dataSet: ArrayList<CalendarEvent>?) :
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val event: LinearLayout

        init {
            // Define click listener for the ViewHolder's View
            event = view.findViewById(R.id.event)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.event_entry, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val time = viewHolder.event.findViewById<TextView>(R.id.event_time)
        time.text = dataSet?.get(position)?.time ?: ""
        val name = viewHolder.event.findViewById<TextView>(R.id.event_name)
        name.text = dataSet?.get(position)?.name ?: ""
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet?.size ?: 0
}