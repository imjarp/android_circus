package com.example.imjarp.androidcircus.views

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class ViewAmountSliderTest {

    private lateinit var amountSlider: ViewAmountSlider

    @Before
    fun setup() {
        amountSlider = ViewAmountSlider(InstrumentationRegistry.getTargetContext())
    }

    @Test
    fun whenTransformScrollToRelativeAmount() {

        val halfExpected = 26_000
        val minAmount = BigDecimal(2_000)
        val maxAmount = BigDecimal(50_000)
        val diffAmount = maxAmount - minAmount


        Assert.assertEquals(halfExpected, amountSlider.calculateMoneyFromPercentage(0.50, diffAmount, minAmount).toInt())
        Assert.assertEquals(minAmount.toInt(), amountSlider.calculateMoneyFromPercentage(0.0, diffAmount, minAmount).toInt())
        Assert.assertEquals(maxAmount.toInt(), amountSlider.calculateMoneyFromPercentage(1.0, diffAmount, minAmount).toInt())
    }


    @Test
    fun whenAmountIsSetGetNextStepRound() {

        val step = 200
        val minAmount = BigDecimal(100)
        val maxAmount = BigDecimal(5_000)
        val moneyRange = ViewAmountSlider.MoneyRangeView(minAmount = minAmount, maxAmount = maxAmount, steps = step)

        // min value
        Assert.assertEquals(minAmount.toInt(), amountSlider.getClosestStep(moneyRange, BigDecimal.ZERO).toInt())

        // max value
        Assert.assertEquals(maxAmount.toInt(), amountSlider.getClosestStep(moneyRange, BigDecimal.valueOf(10_000_000)).toInt())

        //Round up with [700.01,799.99]
        Assert.assertEquals(800, amountSlider.getClosestStep(moneyRange, BigDecimal.valueOf(799.99)).toInt())
        Assert.assertEquals(800, amountSlider.getClosestStep(moneyRange, BigDecimal.valueOf(700.1)).toInt())

        //Round up with [800.01,899.99]
        Assert.assertEquals(800, amountSlider.getClosestStep(moneyRange, BigDecimal.valueOf(800.1)).toInt())
        Assert.assertEquals(800, amountSlider.getClosestStep(moneyRange, BigDecimal.valueOf(899.9)).toInt())

    }
}