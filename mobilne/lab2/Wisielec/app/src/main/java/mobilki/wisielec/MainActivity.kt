package mobilki.wisielec

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    enum class GameState{ PLAYING, FINISHED}

    var tries = 0
    var maxTries = 9
    var guessKeyword = ""
    var guessedCharacters = LinkedHashMap<Char,Boolean>()
    var matched = 0
    var gameState = GameState.FINISHED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListeners()
        resetGame()
    }


    private fun constructGuessString() : Pair<String,Int>{
        var s = ""
        var matched = 0
        for(c in guessKeyword.uppercase()){
            if(guessedCharacters[c] == null){
                s = s.plus('?')
            }else{
                s = s.plus(c)
                matched++
            }
        }
        return Pair(s,matched)
    }

    private fun resetGame(){
        tries = 0
        guessedCharacters.clear()
        val Dictionary: Array<String> = resources.getStringArray(R.array.dictionary)
        val index = kotlin.random.Random.nextInt(0,Dictionary.size)
        guessKeyword = Dictionary[index]
        findViewById<ImageView>(R.id.hangman).setImageResource(R.drawable.wisilec0)
        val (retString, retInt) = constructGuessString()
        findViewById<TextView>(R.id.guessWord).text = retString
        matched = retInt
        gameState = GameState.PLAYING
    }

    private fun setupListeners(){
        findViewById<ImageButton>(R.id.inputButton).setOnClickListener {
            if(gameState == GameState.FINISHED){
                resetGame()
                return@setOnClickListener
            }
            val s = findViewById<EditText>(R.id.inputField).text.toString().uppercase()
            if(s.isEmpty()) return@setOnClickListener

            guessedCharacters[s[0]] = true
            val (retString, retInt) = constructGuessString()
            findViewById<TextView>(R.id.guessWord).text = retString
            if(retInt > matched){
                findViewById<TextView>(R.id.guessWord).text = retString
                matched = retInt
                if( matched == guessKeyword.length){
                    GameState.FINISHED
                    findViewById<ImageView>(R.id.hangman).setImageResource(R.drawable.wisielec_win)
                }
            }
            else{
                tries++
                if(tries <= maxTries){
                    val resName = "wisilec$tries"
                    val drawableResourceId =
                        this.resources.getIdentifier(resName, "drawable", this.packageName)
                        findViewById<ImageView>(R.id.hangman).setImageResource(drawableResourceId)
                }
                if(tries == maxTries){
                    findViewById<TextView>(R.id.guessWord).text = guessKeyword
                    gameState = GameState.FINISHED
                }
            }
        }
    }


}