package com.example.imjarp.androidcircus

import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.imjarp.androidcircus.kotlin.KotlinSamples
import com.example.imjarp.androidcircus.kotlin.SequencesSamples
import com.example.imjarp.androidcircus.views.ViewAmountSlider
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal


class MainActivity : AppCompatActivity(), ViewAmountSlider.StepMoveListener, TextWatcher {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val slider = findViewById<ViewAmountSlider>(R.id.view_slider)
        slider.setView(getMoneyRange(), this)
        val sourceSpan = SpannableString("A")
        val d = ContextCompat.getDrawable(this, R.drawable.circle)!!
        d.setBounds(0, 0, 32, 32)
        val imageSpan = ImageSpan(d, ImageSpan.ALIGN_BOTTOM)
        sourceSpan.setSpan(imageSpan, 0, 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        ed1.hint = sourceSpan
        ed2.hint = sourceSpan
        ed3.hint = sourceSpan
        ed4.hint = sourceSpan

        ed1.addTextChangedListener(this)
        ed2.addTextChangedListener(this)
        ed3.addTextChangedListener(this)
        ed4.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) = Unit

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.length == 1 && ed1.hasFocus()) {
            ed2.postDelayed({ ed2.requestFocus() }, 100)
        } else if (s?.length == 1 && ed2.hasFocus()) {
            ed3.postDelayed({ ed3.requestFocus() }, 100)
        } else if (s?.length == 1 && ed3.hasFocus()) {
            ed4.postDelayed({ ed4.requestFocus() }, 100)
        }
    }


    private fun sample() {
        SequencesSamples().filter(mutableListOf())
        //val sealedSample = SealedSample()
        //sealedSample.test()


        val facebookUser = KotlinSamples.FacebookUser()
        var name = facebookUser.nickname
        name = facebookUser.nickname
        name = facebookUser.nickname

        val fixedUser = KotlinSamples.FixedUser("Sonia")
        name = fixedUser.nickname
        name = fixedUser.nickname
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
