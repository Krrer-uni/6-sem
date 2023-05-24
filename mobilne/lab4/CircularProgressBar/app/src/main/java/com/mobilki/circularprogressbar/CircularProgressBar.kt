package com.mobilki.circularprogressbar

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
//import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

class CircularProgressBar(context: Context, attr: AttributeSet?) : View(context, attr) {
    private var foregroundStroke = 20f
    private var backgroundStroke = 10f
    private var min = 0
    private var max = 100
    private var progress = 0f

    private val startAngle = -90f
    private var foregroundColor = Color.CYAN
    private var backgroundColor = Color.GRAY
    private var rectF: RectF = RectF()
    private lateinit var foregroundPaint: Paint
    private lateinit var backgroundPaint: Paint

    init {
        val typedArray =
            context.theme.obtainStyledAttributes(attr, R.styleable.CircularProgressBar, 0, 0)
        try {
            min = typedArray.getInt(R.styleable.CircularProgressBar_min, min)
            max = typedArray.getInt(R.styleable.CircularProgressBar_max, max)
            progress = typedArray.getInt(R.styleable.CircularProgressBar_progress, progress.toInt()).toFloat()
            foregroundColor = typedArray.getColor(
                R.styleable.CircularProgressBar_foregroundColor,
                foregroundColor
            )
            backgroundColor = typedArray.getColor(
                R.styleable.CircularProgressBar_backgroundColor,
                backgroundColor
            )
            foregroundStroke = typedArray.getDimension(
                R.styleable.CircularProgressBar_foregroundColor,
                foregroundStroke
            )
            backgroundStroke = typedArray.getDimension(
                R.styleable.CircularProgressBar_backgroundColor,
                backgroundStroke
            )

        } catch (e: Exception) {
            Log.e("EXCEPTION", e.toString())
        } finally {
            typedArray.recycle()
        }

        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint.color = adjustAlpha(backgroundColor,0.4f)
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.strokeWidth = backgroundStroke

        foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        foregroundPaint.color = foregroundColor
        foregroundPaint.style = Paint.Style.STROKE
        foregroundPaint.strokeWidth = foregroundStroke
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureHeight = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val measurewidth = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = measureHeight.coerceAtMost(measurewidth)
        setMeasuredDimension(min, min)
        val stroke = backgroundStroke.coerceAtLeast(foregroundStroke)
        rectF.set(0 + stroke / 2, 0 + stroke / 2, min - stroke / 2, min - stroke / 2)
    }

    private fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = (Color.alpha(color) * factor).roundToInt()
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            canvas.drawOval(rectF, backgroundPaint)
            var angle = 360f * (progress - min) / (max - min)
            angle = angle.coerceAtMost(360f).coerceAtLeast(0f)
            canvas.drawArc(rectF, startAngle, angle, false, foregroundPaint);
        };
    }

    fun setProgress(progress : Float){
        this.progress = progress
        this.invalidate()
    }

    fun setProgressWithAnimation(progress: Float){
        val oa = ObjectAnimator.ofFloat(this, "progress",this.progress, progress)
        oa.duration = 1000;
        oa.interpolator = DecelerateInterpolator()
        oa.start()
    }
}