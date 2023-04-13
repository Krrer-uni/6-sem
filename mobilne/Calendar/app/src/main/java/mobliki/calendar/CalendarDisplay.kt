package mobliki.calendar

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate


class CalendarDisplay : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    var date = "${LocalDate.now().dayOfMonth}.${LocalDate.now().monthValue}.${LocalDate.now().year}"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_calendar_view, container, false)
        val calendar = fragment.findViewById<CalendarView>(R.id.calendar)
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(fragment.context, "$dayOfMonth.$month.$year",Toast.LENGTH_SHORT).show()
            val eventlist = parentFragmentManager.findFragmentById(R.id.fragment_event_list) as EventList
            eventlist.changeDate("$dayOfMonth.${month+1}.$year")
        }
        return fragment
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        val eventlist = parentFragmentManager.findFragmentById(R.id.fragment_event_list) as EventList
        eventlist.changeDate(date)
    }
}