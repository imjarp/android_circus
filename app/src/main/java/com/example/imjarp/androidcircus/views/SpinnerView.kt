package com.example.imjarp.androidcircus.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

class SpinnerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val arcBounds = RectF()
    private lateinit var valueAnimator: ValueAnimator
    private var arcSize: Float = 0F
    private var arcStart: Float = 0F
    private val decelerateInterpolator = DecelerateInterpolator()

    private val DEGREES_IN_CIRCLE = 360
    private val ARC_MAX_DEGREES = 180
    private val ARC_START_OFFSET_DEGREES = -90


    init {

        paint.apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.repeatMode = ValueAnimator.RESTART
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 1250L
        valueAnimator.addUpdateListener {

            val progress = it.animatedValue as Float
            arcSize = calculateArcSize(progress)
            arcStart = calculateArcStart(progress)
            invalidate()

        }

        valueAnimator.start()

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        valueAnimator.cancel()
        valueAnimator.removeAllUpdateListeners()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val smallestWidth = Math.min(w, h)
        paint.strokeWidth = smallestWidth * 0.05f
        val paintPadding = paint.strokeWidth / 2f

        arcBounds.set(
                paintPadding + paddingLeft,
                paintPadding + paddingTop,
                w - paintPadding + paddingRight,
                h - paintPadding + paddingBottom
        )

    }

    private fun calculateArcSize(animationProgress: Float): Float {
        val adjustProgress = Math.sin(animationProgress * Math.PI)
        return (adjustProgress * -ARC_MAX_DEGREES).toFloat()
    }

    private fun calculateArcStart(animationProgress: Float): Float {
        val deceleratedProgress = decelerateInterpolator.getInterpolation(animationProgress)
        return ARC_START_OFFSET_DEGREES + deceleratedProgress * DEGREES_IN_CIRCLE
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawArc(arcBounds, arcStart, arcSize, false, paint)
    }
}