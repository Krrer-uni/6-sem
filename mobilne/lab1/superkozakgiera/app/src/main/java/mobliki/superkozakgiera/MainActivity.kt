package mobliki.superkozakgiera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    enum class gameState { playing, ended }

    val game_legth = 10000.toLong()
    var points = 0
    var stateOfGame = gameState.ended
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListeners()
        reset()
    }


    private fun reset() {
        findViewById<TextView>(R.id.points).text = ""
        findViewById<TextView>(R.id.timer).text = getString(R.string.timerFormat).format(10.0.toDouble())
        points = 0
    }

    private fun startGame() {
        findViewById<Button>(R.id.startButton).visibility = View.GONE
        reset()
        stateOfGame = gameState.playing
        object : CountDownTimer(game_legth, 100) {
            override fun onTick(millisUntilFinished: Long) {
                findViewById<TextView>(R.id.timer).text =
                    getString(R.string.timerFormat, (millisUntilFinished / 1000.0))
            }

            override fun onFinish() {
                stateOfGame = gameState.ended
                findViewById<Button>(R.id.startButton).visibility = View.VISIBLE
                findViewById<TextView>(R.id.timer).text =
                    getString(R.string.timerFormat, .0)
            }

        }.start()
    }


    private fun setupListeners() {
        val resetButton = findViewById<TextView>(R.id.startButton)
        val tapButton = findViewById<Button>(R.id.tapButton)

        resetButton.setOnClickListener { startGame() }
        tapButton.setOnClickListener {
            if (stateOfGame == gameState.playing) {
                points++
                if(points < 50){
                    findViewById<TextView>(R.id.points).text = getString(R.string.points_text, points)
                }else if(points < 100){
                    findViewById<TextView>(R.id.points).text = getString(R.string.points_over_50_text, points)
                }else{
                    findViewById<TextView>(R.id.points).text = getString(R.string.points_over_100_text, points)
                }
                findViewById<TextView>(R.id.points).textSize = 30 + points.toFloat()/2
            }
        }
    }
}