package mobliki.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EventList : Fragment() {
    var events_storage = HashMap<String, ArrayList<CalendarEvent>>()
    lateinit var adapter: EventListAdapter
    lateinit var recyclerView: RecyclerView
    var eventData : ArrayList<CalendarEvent>? = ArrayList<CalendarEvent>()
    var date = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_event_list, container, false)

        recyclerView = fragment.findViewById<RecyclerView>(R.id.event_list)
        recyclerView.layoutManager = LinearLayoutManager(fragment.context)
        adapter = EventListAdapter(eventData)
        recyclerView.adapter = adapter
        chagneDate("")

        val button = fragment.findViewById<Button>(R.id.add_event_button)
        button.setOnClickListener(){
            addEvent("siema", "dzisiaj")
        }

//        events_storage.put("2023 04 12",ArrayList())
//        for(i in 1..20) {
//            events_storage["2023 04 12"]?.add(CalendarEvent("$i:00", "Event $i"))
//        }
//        adapter =
//        if(events_storage.containsKey("2023 04 12")){
//            adapter =events_storage["2023 04 12"]?.let { EventListAdapter(it) }
//                recyclerView.adapter = adapter        }



        return fragment
    }

    fun chagneDate(new_date : String){
        date = new_date
        eventData?.clear()
        events_storage[date]?.let { eventData?.addAll(it) }

    }

    fun addEvent(name : String, time : String){
        if(!events_storage.containsKey(date)){
            events_storage.put(date,ArrayList())
        }
        eventData?.add(0,CalendarEvent(time,name))
        events_storage[date]!!.add(0,CalendarEvent(time,name))
        adapter.notifyDataSetChanged()

    }
}