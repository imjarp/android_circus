package com.example.imjarp.androidcircus.views

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import com.example.imjarp.androidcircus.R
import java.math.BigDecimal

class ViewAmountSlider : ConstraintLayout {

    private var frontView: TextView? = null
    private var parentView: View? = null
    private var maxWidthView = 0
    private var deltaX: Int = 0
    private var startPoint = 0
    private var halfWidthIndicator = 0
    private var widthIndicator: Int = 0
    private var move = 0
    private var centerPoint: Int = 0
    private var maxMove: Int = 0
    private var maxCenterPoint: Int = 0
    private val tag = "circus_touch"
    private lateinit var moneyRangeView: MoneyRangeView
    private var stepMoveListener: StepMoveListener? = null

    constructor(context: Context) : super(context) {
        init(null, 0, context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0, context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle, context)
    }

    fun setView(moneyRangeView: MoneyRangeView, stepMoveListener: StepMoveListener) {
        this.moneyRangeView = moneyRangeView
        this.stepMoveListener = stepMoveListener
        moveToStart(moneyRangeView.minAmount)
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun init(attrs: AttributeSet?, defStyle: Int, context: Context) {
        parentView = LayoutInflater.from(context).inflate(R.layout.sample_view_drag, this, true)
        frontView = parentView!!.findViewById(R.id.front_view)
        val backView = parentView!!.findViewById<View>(R.id.back_view)

        frontView?.afterMeasured {
            widthIndicator = width
            startPoint = (width / 2)
            halfWidthIndicator = width / 2
        }
        backView?.afterMeasured { maxWidthView = width }

        parentView = findViewById(R.id.main_layout)

        frontView?.setOnTouchListener { v, event ->
            moveView(event, v)
            true
        }
    }

    private fun moveView(event: MotionEvent, v: View) {

        val params = v.layoutParams as ConstraintLayout.LayoutParams

        val rawX = event.rawX.toInt()
        if (maxMove == 0) maxMove = maxWidthView - widthIndicator
        if (maxCenterPoint == 0) maxCenterPoint = maxWidthView - halfWidthIndicator


        when (event.action and MotionEvent.ACTION_MASK) {

            MotionEvent.ACTION_DOWN -> {
                deltaX = rawX - params.leftMargin
                return
            }

            MotionEvent.ACTION_MOVE -> {
                centerPoint = getCenterPoint(event.x.toInt(), rawX, halfWidthIndicator)

                move = if (centerPoint >= maxCenterPoint) {
                    maxMove
                } else {
                    rawX - deltaX
                }

                val closeStepAmount = getAmountFromRelativeScroll(centerPoint)
                frontView?.text = "$${closeStepAmount.toInt()}"
                stepMoveListener?.onStepMove(StepMove(moneyRangeView.steps, closeStepAmount))

                params.leftMargin = move
                params.rightMargin = 0
                v.layoutParams = params

            }
        }
        parentView?.invalidate()
    }

    private fun moveToStart(minAmount: BigDecimal) {
        val params = frontView?.layoutParams as ConstraintLayout.LayoutParams
        frontView?.text = "$${minAmount.toInt()}"
        params.leftMargin = move
        params.rightMargin = 0
        frontView?.layoutParams = params
        parentView?.invalidate()
        stepMoveListener?.onStepMove(StepMove(moneyRangeView.steps, minAmount))
    }

    private fun calculatePercentageScroll(scrollX: Int): Double = (scrollX.toDouble() - halfWidthIndicator) / maxMove.toDouble()

    @VisibleForTesting
    fun calculateMoneyFromPercentage(percentageScroll: Double, diffAmount: BigDecimal, minAmount: BigDecimal): BigDecimal {
        val amount = BigDecimal(diffAmount.toDouble() * percentageScroll)
        return minAmount + amount
    }

    @VisibleForTesting
    fun getClosestStep(moneyRangeView: MoneyRangeView, amount: BigDecimal): BigDecimal {
        if (amount <= moneyRangeView.minAmount) return moneyRangeView.minAmount
        if (amount >= moneyRangeView.maxAmount) return moneyRangeView.maxAmount

        val rem = amount.rem(BigDecimal(moneyRangeView.steps))
        val halfStep = BigDecimal(moneyRangeView.steps.toDouble().div(2.toDouble()))

        return if (halfStep < rem) {
            //Round Up
            (amount - rem) + BigDecimal(moneyRangeView.steps)
        } else {

            //Round Down
            amount - rem
        }
    }

    private fun getAmountFromRelativeScroll(scrollX: Int): BigDecimal {
        val amountByScroll = calculateMoneyFromPercentage(
                percentageScroll = calculatePercentageScroll(scrollX),
                diffAmount = moneyRangeView.diffAmount,
                minAmount = moneyRangeView.minAmount)

        return getClosestStep(moneyRangeView, amountByScroll)
    }


    private inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        })
    }

    companion object {


        /** Get the center point of the view get relative to the touch
        example:

        [ ] = View
        x represent the touch
        C will be calculated

        | -----[ x C   ] ----------------------------|
         **/

        @JvmStatic
        fun getCenterPoint(xTouchPoint: Int,
                           xTouchRaw: Int,
                           half: Int): Int {

            return if (xTouchPoint > half) {
                (half.minus(xTouchPoint)) + xTouchRaw

            } else {
                (half.minus(xTouchPoint)) + xTouchRaw
            }

        }
    }

    data class MoneyRangeView(val minAmount: BigDecimal,
                              val maxAmount: BigDecimal,
                              val steps: Int,
                              internal val diffAmount: BigDecimal = maxAmount - minAmount)

    data class StepMove(val step: Int,
                        val amountInStep: BigDecimal)


    interface StepMoveListener {
        fun onStepMove(stepMove: StepMove)
    }

}
