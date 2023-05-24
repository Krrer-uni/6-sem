package com.mobilki.circularprogressbar

import android.app.Activity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobilki.circularprogressbar.ui.theme.CircularProgressBarTheme

class MainActivity : Activity() {

    lateinit var progressBar: CircularProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progress_bar_test)

        progressBar = findViewById<CircularProgressBar>(R.id.custom_progress_bar)
        val seekbar = findViewById<SeekBar>(R.id.seekBar)
        seekbar.setOnSeekBarChangeListener(seekbarListener(progressBar = progressBar))
    }
    class seekbarListener(val progressBar: CircularProgressBar) : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            progressBar.setProgressWithAnimation(progress = progress.toFloat())
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }

    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CircularProgressBarTheme {
        Greeting("Android")
    }
}