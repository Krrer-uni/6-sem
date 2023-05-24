package com.mobilki.studentinvaders

// GameThread.kt

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameThread(private val surfaceHolder: SurfaceHolder, private val gameView: GameView) :
    Thread() {

    var running = false

    override fun run() {
        while (running) {
            val canvas: Canvas? = surfaceHolder.lockCanvas()
            if (canvas != null) {
                gameView.draw(canvas)
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }
}
