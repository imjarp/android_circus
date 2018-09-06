package com.example.imjarp.androidcircus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.imjarp.androidcircus.views.ViewAmountSlider
import java.math.BigDecimal

class MainActivity : AppCompatActivity(), ViewAmountSlider.StepMoveListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val slider = findViewById<ViewAmountSlider>(R.id.view_slider)
        slider.setView(getMoneyRange(), this)
    }

    private fun getMoneyRange(): ViewAmountSlider.MoneyRangeView {
        return ViewAmountSlider.MoneyRangeView(BigDecimal(2_000),
                BigDecimal(50_000),
                500)
    }

    override fun onStepMove(stepMove: ViewAmountSlider.StepMove) {
        Log.d("circus_touch", stepMove.amountInStep.toPlainString())
    }

}
