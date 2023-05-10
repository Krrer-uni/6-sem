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
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class CalendarDisplay : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    var date = "${LocalDate.now().dayOfMonth}.${LocalDate.now().monthValue}.${LocalDate.now().year}"
    var calendar_date : Long = 0
    lateinit var calendar: CalendarView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment = inflater.inflate(R.layout.fragment_calendar_view, container, false)
        calendar = fragment.findViewById<CalendarView>(R.id.calendar)



        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val eventlist = parentFragmentManager.findFragmentById(R.id.fragment_event_list) as EventList
            eventlist.changeDate("$dayOfMonth.${month+1}.$year")
            calendar_date = calendar.date
        }

        return fragment
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        val eventlist = parentFragmentManager.findFragmentById(R.id.fragment_event_list) as EventList
<<<<<<< Updated upstream
=======
//        eventlist.changeDate(date)
>>>>>>> Stashed changes
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("calendar_date", calendar.date)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            calendar.setDate(savedInstanceState.getLong("calendar_date"),true,true)
        }
    }
}
