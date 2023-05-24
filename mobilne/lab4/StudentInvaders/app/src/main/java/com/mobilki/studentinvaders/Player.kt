package com.mobilki.studentinvaders

// Player.kt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Player(context: Context, val bitmap: Bitmap) {

    var x: Float = 0f
    var y: Float = 0f
    private var speedX: Float = 0f
    private var speedY: Float = 0f
    private var targetX : Float = 0f
    private var targetY : Float = 0f
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    var width: Int = 200
    var height: Int = 200

    init {
    }

    fun setScreenSize(width : Int ,height : Int){
        screenWidth = width
        screenHeight = height
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x - width/2
        this.y = y - width/2
        targetX = x
        targetY = y
    }

    fun moveTo(targetX: Float, targetY: Float) {
        this.targetX = targetX
        this.targetY = targetY
    }

    fun stopMoving() {
        targetX = -0f
        targetY = -0f
    }

    fun update(dt : Float) {
        if(targetX != -0f || targetY != -0f){
            val distanceX = targetX - (x + width/2)
            val distanceY = targetY - (y + height/2)
            val maxSpeed = 15f * 100f
            var distance = sqrt(distanceX.pow(2) + distanceY.pow(2))
            val maxDistance = sqrt(screenHeight.toFloat().pow(2) + screenWidth.toFloat().pow(2))
            var speed = (sin(distance / maxDistance * PI.toFloat())) * maxSpeed

            if(distance == 0f) distance = 1f;
            speedX = speed * distanceX/distance
            speedY = speed * distanceY/distance
        }

        x += speedX * dt
        y += speedY * dt
        speedX *= 0.9f
        speedY *= 0.9f

        // Ensure the player stays within the screen bounds
        if (x < 0) {
            x = 0f
        } else if (x + width > screenWidth) {
            x = (screenWidth - width).toFloat()
        }

        if (y < 0) {
            y = 0f
        } else if (y + height > screenHeight) {
            y = (screenHeight - height).toFloat()
        }
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, width,height,false), x, y, null)
    }
}
