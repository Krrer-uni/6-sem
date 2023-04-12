package mobliki.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast


class CalendarDisplay : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_calendar_view, container, false)
        val calendar = fragment.findViewById<CalendarView>(R.id.calendar)
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(fragment.context, "$year $month $dayOfMonth",Toast.LENGTH_SHORT).show()
            val eventlist = parentFragmentManager
        }
        return fragment
    }

}
