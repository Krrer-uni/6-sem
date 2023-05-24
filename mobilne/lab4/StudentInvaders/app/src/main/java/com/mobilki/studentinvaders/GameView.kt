package com.mobilki.studentinvaders

// GameView.kt
// GameView.kt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.MotionEvent
import android.view.SurfaceHolder.Callback2
import java.lang.Integer.max
import java.lang.Integer.min

class GameView(context: Context) : SurfaceView(context), Callback2 {

    private var thread: GameThread? = null
    private var player: Player? = null
    private var bullets: MutableList<Bullet> = mutableListOf()
    private var enemies: MutableList<Enemy> = mutableListOf()
    private var isGameOver: Boolean = false
    private var hasGameStarted: Boolean = false
    private var namePicker: NamePicker? = null
    private var scoreDb = ScoreDatabase.getDatabase(context)
    private var scoreDao = scoreDb.scoreDao()

    private var score: Int = 0
    private var frameRate: Int = 20
    private var framesPerBullet: Int = 40 //frames per bullet
    private var bulletFrameCounter: Int = 0
    private var w: Int = 0
    private var h: Int = 0
    private lateinit var bullet_bitmap: Bitmap
    private lateinit var enemy_bitmap: Bitmap
    private lateinit var ship_bitmap: Bitmap
    private var numRows = 1
    private var enemyLifes = 1

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        this.w = w
        this.h = h

        super.onSizeChanged(w, h, oldw, oldh)
    }

    init {
        holder.addCallback(this)
    }

    fun resume() {
        thread = GameThread(holder, this)
        thread?.running = true
        thread?.start()
    }

    fun pause() {
        thread?.running = false
        try {
            thread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        bullet_bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.space_invaders_bullet)
        ship_bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.space_invaders_ship)
        enemy_bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.space_invaders_alien)
        player = Player(context, ship_bitmap)
        player!!.setScreenSize(w, h)
        player!!.setPosition((w / 2).toFloat(), (9f * h / 10f))
        generateEnemies(numRows)
        hasGameStarted = true
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Respond to surface changes (e.g., screen rotation) here
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // Release game resources (e.g., bitmaps, sounds) here
    }

    override fun surfaceRedrawNeeded(holder: SurfaceHolder) {
        //
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        // Draw game objects (e.g., player, enemies, bullets) on the canvas here
        canvas.drawColor(Color.BLACK) // Example background color
        player?.draw(canvas)
        for (bullet in bullets) {
            bullet.draw(canvas)
        }
        for (enemy in enemies) {
            enemy.draw(canvas)
        }
        drawScore(canvas)
        if (isGameOver) {
            drawGameOver(canvas)
//            namePicker?.draw()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isGameOver) {
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    player?.moveTo(event.x, event.y)
                }

                MotionEvent.ACTION_UP -> {
                    player?.stopMoving()
                }
            }
            return true
        }
//        if(event.action == MotionEvent.ACTION_DOWN) {
//            player = Player(context, ship_bitmap)
//            player!!.setScreenSize(w, h)
//            player!!.setPosition((w / 2).toFloat(), (9f * h / 10f))
//            generateEnemies(numRows)
//            score = 0
//        }
        return super.onTouchEvent(event)
    }

    private fun generateEnemies(numRows: Int = 3) {
        val enemySpacing = 150
        val enemySize = 100
        val numCols = w / (enemySize + enemySpacing)
        val startX = (w - numCols * (enemySize + enemySpacing)) / 2
        val startY = 200
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                val enemy = Enemy(context, enemy_bitmap, enemyLifes)
                val x = startX + col * (enemySize + enemySpacing)
                val y = startY + row * (enemySize + enemySpacing)
                enemy.setPosition(x.toFloat(), y.toFloat())
                enemy.setSpeed(40f, 10f)
                enemy.setScreenSize(w, h)
                enemies.add(enemy)
            }
        }
    }

    private fun drawScore(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = 48f
        canvas.drawText("Score: $score", 50f, 100f, paint)
    }

    private fun drawGameOver(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = 80f
        val gameOverText = "Game Over"
        val textWidth = paint.measureText(gameOverText)
        canvas.drawText(gameOverText, (width - textWidth) / 2, height / 2f, paint)
        val placeholder = scoreDao.top3()
//        if(score > placeholder.last().score){
//            namePicker = NamePicker()
//
//        }
        val scoreWidth = paint.measureText(placeholder[0].toString())
        for (i in 0..min(4,placeholder.size-1)) {
            val scoreText = placeholder[i].score.toString()
            canvas.drawText(scoreText, width / 2f, height / 2f + (i+1) * 100f, paint)
        }
    }

    private fun update(time_step: Float) {
        if (!isGameOver) {
            player?.update(time_step)
            bulletFrameCounter++
            if (bulletFrameCounter == framesPerBullet) {
                val bullet = Bullet(context, h, bullet_bitmap)
                bullet.setPosition(
                    player!!.x + player!!.width / 2,
                    player!!.y + player!!.height / 2
                )
                bulletFrameCounter = 0;
                bullets.add(bullet)
            }
            for (bullet in bullets) {
                bullet.update(time_step)
            }
            for (enemy in enemies) {
                enemy.update(time_step)
                if (enemy.isColliding(player)) {
                    gameOver()
                    return
                }
                for (bullet in bullets) {
                    if (bullet.isColliding(enemy)) {
                        bullet.isActive = false
                        enemy.hitpoints--
                        score += 10
                        break
                    }
                }
            }
//            for (bullet in bullets) {
//                if(!bullet.isActive()){
//                    bullets.remove(bullet)
//                }
//            }
            enemies.removeIf { enemy -> enemy.hitpoints == 0 }
            bullets.removeIf { bullet -> !bullet.isActive }
            if (enemies.isEmpty() && hasGameStarted) {
                if (enemyLifes % numRows == 0) {
                    enemyLifes = 1
                    numRows++
                } else {
                    enemyLifes++;
                }

                generateEnemies(numRows = numRows)
                framesPerBullet = max(2, (0.9 * framesPerBullet).toInt())
            }
        }
    }

    private fun gameOver() {
        isGameOver = true
        scoreDao.insertAll(Score(0,"as", score))
        // Save highscore to the Room database here
    }

    inner class GameThread(
        private val surfaceHolder: SurfaceHolder,
        private val gameView: GameView
    ) :
        Thread() {

        var running = false


        var currentTimeMillis = System.currentTimeMillis()
        override fun run() {
            while (running) {
                val canvas: Canvas? = surfaceHolder.lockCanvas()
                val threadWait =
                    (1000f / frameRate) - (System.currentTimeMillis() - currentTimeMillis)
//                Log.e("asd",threadWait.toString())
                currentTimeMillis = System.currentTimeMillis()
                if (canvas != null) {
                    gameView.update(Math.min(1f / frameRate, threadWait.toFloat() / 1000))
                    gameView.draw(canvas)
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }

//                if(threadWait < 0) continue
//                sleep(threadWait)
            }
        }
    }
}
