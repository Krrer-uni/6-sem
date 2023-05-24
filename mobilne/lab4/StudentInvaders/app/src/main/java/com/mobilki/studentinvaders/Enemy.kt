package com.mobilki.studentinvaders

// Enemy.kt
// Enemy.kt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect

class Enemy(context: Context,var bitmap: Bitmap, var hitpoints : Int) {

    var x: Float = 0f
    var y: Float = 0f
    private var speedX: Float = 0f
    private var speedY: Float = 0f
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    var isActive = true
    var width: Int = 0
    var height: Int = 0
    init {
        width = 100
        height = 100
    }

    fun setScreenSize(width : Int ,height : Int){
        screenWidth = width
        screenHeight = height
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun setSpeed(speedX: Float, speedY: Float) {
        this.speedX = speedX
        this.speedY = speedY
    }

    fun update(time_step: Float) {
        x += speedX * time_step
        y += speedY * time_step

        // Reverse direction if enemy reaches screen bounds
        if (x < 0 || x + width > screenWidth) {
            speedX = -speedX
        }

        // Optional: Wrap around to the opposite side if enemy reaches screen bounds
        // if (x < -width) {
        //     x = screenWidth.toFloat()
        // } else if (x > screenWidth) {
        //     x = -width.toFloat()
        // }
    }

    fun isColliding(player: Player?): Boolean {
        if (player == null) return false
        val playerRect = Rect(player.x.toInt(), player.y.toInt(), (player.x + player.width).toInt(), (player.y + player.height).toInt())
        val enemyRect = Rect(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt())
        return playerRect.intersect(enemyRect)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap,width,height,false), x, y, null)
    }
}
