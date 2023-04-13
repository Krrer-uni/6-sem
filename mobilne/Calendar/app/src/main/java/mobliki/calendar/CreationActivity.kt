package mobliki.calendar

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalTime

class CreationActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_creator_activity)
        val save_button = findViewById<Button>(R.id.creator_button_1)
        val cancel_button = findViewById<Button>(R.id.creator_button_2)
        val delete_button = findViewById<Button>(R.id.creator_button_3)
        val name_text = findViewById<EditText>(R.id.editTextTextPersonName)
        val time_text = findViewById<EditText>(R.id.editTextTime)
        val resultIntent = Intent(this, MainActivity::class.java)
        val now = LocalTime.now()
        val hour = now.hour
        val minute = now.minute
        time_text.hint = "$hour:$minute"

        if(intent.getIntExtra("requestCode", -1)== CREATE_MODE){
            delete_button.visibility = View.INVISIBLE
        }else {
            val pos = intent.getIntExtra("position", 0)
            resultIntent.putExtra("position", pos)
            name_text.setText(intent.getStringExtra("name"))
            time_text.setText(intent.getStringExtra("time"))
        }

        save_button.setOnClickListener{
            resultIntent.putExtra("event_name",name_text.text.toString())
            resultIntent.putExtra("event_time",time_text.text.toString())
            setResult(CREATE, resultIntent)
            finish()
        }

        cancel_button.setOnClickListener{
            setResult(CANCEL, resultIntent)
            finish()
        }

        delete_button.setOnClickListener{
            setResult(DELETE, resultIntent)
            finish()
        }
    }

}