package lab1.zad1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var rollMax = 10
    private var points = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClickListeners()
        generateNumbers()
        findViewById<TextView>(R.id.Points).text = getString(R.string.points_string,0)
    }

    private fun generateNumbers() {
        val r1 = Random.nextInt(rollMax)
        val r2 = Random.nextInt(rollMax)
        findViewById<Button>(R.id.buttonLeft).text = r1.toString()
        findViewById<Button>(R.id.buttonRight).text = r2.toString()
    }


    private fun checkAnswer(answer: Int, other: Int) {
        if (answer > other) {
            Toast.makeText(this@MainActivity, "Correct!", Toast.LENGTH_SHORT).show()
            points++
            findViewById<TextView>(R.id.Points).text = getString(R.string.points_string, points)
        } else {
            Toast.makeText(this@MainActivity, "Wrong!", Toast.LENGTH_SHORT).show()
        }
        generateNumbers()
    }

    private fun setClickListeners() {
        val leftButton = findViewById<Button>(R.id.buttonLeft)
        val rightButton = findViewById<Button>(R.id.buttonRight)
        leftButton.setOnClickListener {
            checkAnswer(
                Integer.parseInt(leftButton.text.toString()),
                Integer.parseInt(rightButton.text.toString())
            )
        }
        rightButton.setOnClickListener {
            checkAnswer(
                Integer.parseInt(rightButton.text.toString()),
                Integer.parseInt(leftButton.text.toString())
            )
        }
    }


}