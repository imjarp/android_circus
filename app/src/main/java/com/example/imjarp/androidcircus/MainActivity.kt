package com.example.imjarp.androidcircus

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import com.example.imjarp.androidcircus.views.NotifyingEditText


class MainActivity : AppCompatActivity() {
    lateinit var edViews: MutableList<NotifyingEditText>
    val cameraCode = 1
    val cameraValue = 109

    override fun getCurrentFocus(): View? {
        return super.getCurrentFocus()
    }

    fun isValid(): Boolean {
        return edViews.all { !TextUtils.isEmpty(it.text) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val ed1 = findViewById<NotifyingEditText>(R.id.ed1)
        val ed2 = findViewById<NotifyingEditText>(R.id.ed2)
        val ed3 = findViewById<NotifyingEditText>(R.id.ed3)
        val ed4 = findViewById<NotifyingEditText>(R.id.ed4)

        edViews = mutableListOf()
        edViews.addAll(listOf(ed1, ed2, ed3, ed4))


        edViews.forEach {
            it.clearFocus()
        }

        val onEditorActionListener = OnEditorActionListener { v, actionId, event ->
            if (actionId == 5 && !this.isValid()) {
                focusOnNext(v as EditText)
                //Toast.makeText(this, "Finish", Toast.LENGTH_SHORT).show()
            }

            true
        }

        val onKeyListener = object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                val confirmationCodeView = v as EditText
                if (keyCode >= 7 && keyCode <= 16 && event.action == 0) {
                    val text = Character.toString(event.unicodeChar.toChar())
                    confirmationCodeView.setText(text)
                    return true
                } else if (keyCode == 67 && event.action == 0) {
                    if (confirmationCodeView.text.length == 0) {
                        val previous = focusOnPrevious(confirmationCodeView)
                        previous?.setText("")

                    } else {
                        confirmationCodeView.setText("")
                    }

                    return true
                } else {
                    return false
                }
            }
        }


        val edits = edViews

        edits.forEachIndexed { index, notifyingEditText ->

            notifyingEditText.setRawInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD)
            notifyingEditText.setOnEditorActionListener(onEditorActionListener)
            notifyingEditText.setOnKeyListener(onKeyListener)
            notifyingEditText.setOnSoftKeyListener(onKeyListener)
            notifyingEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        focusOnNext(notifyingEditText)
                    }
                }

            })

        }

        ed1.requestFocus()

    }

    private fun focusOnPrevious(confirmationCodeView: EditText): EditText? {
        var edit: EditText? = null
        edViews.forEachIndexed { index, notifyingEditText ->
            if (confirmationCodeView.id == notifyingEditText.id && index > 0) {
                edit = edViews[index - 1]
            }
        }
        edit?.requestFocus()
        return edit
    }

    private fun focusOnNext(confirmationCodeView: EditText): EditText? {
        var edit: EditText? = null
        edViews.forEachIndexed { index, notifyingEditText ->
            if (confirmationCodeView.id == notifyingEditText.id && index < edViews.size) {
                edit = edViews[index + 1]
            }
        }
        edit?.requestFocus()
        return edit
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}
