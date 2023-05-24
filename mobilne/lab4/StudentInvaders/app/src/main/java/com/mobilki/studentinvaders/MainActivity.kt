package com.mobilki.studentinvaders

import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide the status bar for full-screen gameplay
        window.setDecorFitsSystemWindows(false)
//        window.insetsController?.let {
//            it.hide(WindowInsets.Type.statusBars())
//            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }

        // Initialize the game view
        gameView = GameView(this)
        setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }
}