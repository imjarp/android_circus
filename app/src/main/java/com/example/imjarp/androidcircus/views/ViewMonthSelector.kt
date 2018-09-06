package com.example.imjarp.androidcircus.views

import android.annotation.SuppressLint
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.TranslateAnimation
import com.example.imjarp.androidcircus.R

class ViewMonthSelector : ConstraintLayout, View.OnClickListener {


    private var frontView: View? = null
    private var parentView: View? = null
    private var maxWidthView = 0
    private var widthIndicator: Int = 0
    private var monthThree: View? = null
    private var monthSix: View? = null
    private var monthNine: View? = null
    private var monthTwelve: View? = null
    private var listener: MonthSelectionListener? = null
    private val interpolator = AccelerateDecelerateInterpolator()
    private val animationDuration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
    private var set = ConstraintSet()
    private val tag = "circus_touch"

    constructor(context: Context) : super(context) {
        init(null, 0, context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0, context)
    }


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle, context)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init(attrs: AttributeSet?, defStyle: Int, context: Context) {
        parentView = LayoutInflater.from(context).inflate(R.layout.view_month_selector, this, true)
        frontView = parentView!!.findViewById(R.id.front_view)
        val backView = parentView!!.findViewById<View>(R.id.back_view)

        frontView?.afterMeasured {
            widthIndicator = width
        }
        backView?.afterMeasured { maxWidthView = width }

        parentView = findViewById(R.id.main_layout)

        monthThree = findViewById(R.id.txt_three)
        monthThree?.setOnClickListener(this)

        monthSix = findViewById(R.id.txt_six)
        monthSix?.setOnClickListener(this)

        monthNine = findViewById(R.id.txt_nine)
        monthNine?.setOnClickListener(this)

        monthTwelve = findViewById(R.id.txt_twelve)
        monthTwelve?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        setPreviousMonthUnselected()
        v?.isSelected = true
        val monthSelected = getSelectedMonthEnum(v)
        moveFrontView(monthSelected)


        listener?.onMonthSelected(monthSelected)
    }

    private fun moveFrontView(monthSelected: MonthEnum) {


        val translateAnimation = TranslateAnimation(100F, 540F, 0F, 0F)
        frontView?.animation = translateAnimation
        translateAnimation.interpolator = interpolator
        translateAnimation.duration = animationDuration
        translateAnimation.fillAfter = true
        translateAnimation.start()

    }

    private fun getTranslationPoint(monthSelected: MonthEnum): Int {
        return when (monthSelected) {
            MonthEnum.THREE -> 0
            MonthEnum.SIX -> 270
            MonthEnum.NINE -> 540
            MonthEnum.TWELVE -> 810
        }
    }

    private fun getSelectedMonthEnum(v: View?): MonthEnum {
        return when (v?.id) {
            R.id.txt_three -> MonthEnum.THREE
            R.id.txt_six -> MonthEnum.SIX
            R.id.txt_nine -> MonthEnum.NINE
            R.id.txt_twelve -> MonthEnum.TWELVE
            else -> throw  Exception("Unknown view")
        }
    }

    private fun setPreviousMonthUnselected() {
        when {
            monthThree?.isSelected == true -> monthThree?.isSelected = false
            monthSix?.isSelected == true -> monthSix?.isSelected = false
            monthNine?.isSelected == true -> monthNine?.isSelected = false
            monthTwelve?.isSelected == true -> monthTwelve?.isSelected = false
        }
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

    interface MonthSelectionListener {
        fun onMonthSelected(month: MonthEnum)
    }

    enum class MonthEnum {
        THREE,
        SIX,
        NINE,
        TWELVE
    }

}
