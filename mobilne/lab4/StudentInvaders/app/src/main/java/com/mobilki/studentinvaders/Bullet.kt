package com.mobilki.studentinvaders

// Bullet.kt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect

class Bullet(context: Context, private val screenheight: Int, private val bitmap:Bitmap) {

    private var x: Float = 0f
    private var y: Float = 0f
    private var speedY: Float = -400f
    private var width: Int = 0
    private var height: Int = 0
    var isActive: Boolean = true

    init {
        width = 50
        height = 50
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x - width / 2
        this.y = y - height
    }

    fun update(time_step: Float) {
        y += speedY * time_step

        // Disable the bullet if it goes off the screen
        if (y + height < 0) {
            isActive = false
        }
    }

    fun isColliding(enemy: Enemy): Boolean {
        val bulletRect = Rect(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt())
        val enemyRect = Rect(enemy.x.toInt(), enemy.y.toInt(), (enemy.x + enemy.width).toInt(), (enemy.y + enemy.height).toInt())
        return bulletRect.intersect(enemyRect)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap,width,height,false), x, y, null)
    }


}
