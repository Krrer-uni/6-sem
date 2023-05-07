package mobliki.calendar

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


@RequiresApi(Build.VERSION_CODES.O)
class EventList : Fragment() {




    var events_storage = HashMap<String, ArrayList<CalendarEvent>>()
    lateinit var adapter: EventListAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var listtext: TextView
    var eventData : ArrayList<CalendarEvent>? = ArrayList()
    var date = "${LocalDate.now().dayOfMonth}.${LocalDate.now().monthValue}.${LocalDate.now().year}"


    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        Log.e("getcontent",uri.toString())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_event_list, container, false)

        listtext = fragment.findViewById(R.id.event_list_title)
        recyclerView = fragment.findViewById(R.id.event_list)
        recyclerView.layoutManager = LinearLayoutManager(fragment.context)
        adapter = EventListAdapter(eventData, this::eventClickListener)
        recyclerView.adapter = adapter

        changeDate(date)

        val button = fragment.findViewById<Button>(R.id.add_event_button)
        button.setOnClickListener(){
            buttonListener(view)
        }

        return fragment
    }

    private fun eventClickListener(position :Int){
        val myIntent = Intent(view!!.context,CreationActivity::class.java)
        myIntent.putExtra("date",date)
        myIntent.putExtra("name", eventData?.get(position)?.name)
        myIntent.putExtra("time", eventData?.get(position)?.time)


        myIntent.putExtra("position",position)
        myIntent.putExtra("requestCode", MODIFY_MODE)
        startActivityForResult(myIntent, MODIFY_MODE)
    }
    private fun buttonListener(view: View?){
        val myIntent = Intent(view!!.context,CreationActivity::class.java)
        myIntent.putExtra("date",date)
        myIntent.putExtra("requestCode", CREATE_MODE)
        startActivityForResult(myIntent, CREATE_MODE)

    }
    @SuppressLint("NotifyDataSetChanged")
    fun changeDate(new_date : String){
        date = new_date
        eventData?.clear()
        events_storage[date]?.let { eventData?.addAll(it) }
        adapter.notifyDataSetChanged()
        listtext.text = "Events for $date"
    }

    private fun addEvent(name : String, time : String){
        if(!events_storage.containsKey(date)){
            events_storage.put(date,ArrayList())
        }
        eventData?.add(0,CalendarEvent(time,name))
        events_storage[date]!!.add(0,CalendarEvent(time,name))
        adapter.notifyItemInserted(0)

    }
    private fun removeEvent(position: Int){
        if(!events_storage.containsKey(date)){
            return
        }
        eventData?.removeAt(position)
        events_storage[date]!!.removeAt(0)
        adapter.notifyItemRemoved(position)
    }
    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CREATE_MODE -> {
                when(resultCode){
                    CREATE ->{
                        if(data == null)
                            return
                        val name = data.getStringExtra("event_name")
                        val time = data.getStringExtra("event_time")
                        addEvent(name!!,time!!)
                    }
                }
            }
            MODIFY_MODE ->{
                when(resultCode){
                    CREATE ->{
                        if(data == null)
                            return
                        val name = data.getStringExtra("event_name")
                        val time = data.getStringExtra("event_time")
                        val position = data.getIntExtra("position",-1)
                        if(position == -1) return
                        removeEvent(position)
                        addEvent(name!!,time!!)
                    }
                    DELETE ->{
                        if(data == null)
                            return
                        val position = data.getIntExtra("position",-1)
                        if(position == -1) return
                        removeEvent(position)
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("event_data",events_storage)
        outState.putString("date",date)
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            events_storage = savedInstanceState.getSerializable("event_data") as HashMap<String, ArrayList<CalendarEvent>>
            date = savedInstanceState.getString("date").toString()
        }
    }
}



