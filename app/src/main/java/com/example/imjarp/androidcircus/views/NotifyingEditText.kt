package com.example.imjarp.androidcircus.views

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import androidx.appcompat.widget.AppCompatEditText

class NotifyingEditText : AppCompatEditText {
    private var onSoftKeyListener: OnKeyListener? = null
    private var pasteListener: NotifyingEditText.PasteListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    fun setOnSoftKeyListener(onSoftKeyListener: OnKeyListener) {
        this.onSoftKeyListener = onSoftKeyListener
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        val result = super.onTextContextMenuItem(id)
        when (id) {
            16908322 -> {
                if (this.pasteListener != null) {
                    this.pasteListener!!.onTextPaste()
                }
                return result
            }
            else -> return result
        }
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        return NotifyingInputConnection(super.onCreateInputConnection(outAttrs), true)
    }

    fun setPasteListener(pasteHandler: NotifyingEditText.PasteListener) {
        this.pasteListener = pasteHandler
    }

    private inner class NotifyingInputConnection(target: InputConnection, mutable: Boolean) : InputConnectionWrapper(target, mutable) {

        override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
            if (this@NotifyingEditText.onSoftKeyListener != null) {
                var handled = this@NotifyingEditText.onSoftKeyListener!!.onKey(this@NotifyingEditText, 67, KeyEvent(0, 67))
                handled = this@NotifyingEditText.onSoftKeyListener!!.onKey(this@NotifyingEditText, 67, KeyEvent(1, 67)) || handled
                if (handled) {
                    return true
                }
            }

            return super.deleteSurroundingText(beforeLength, afterLength)
        }

        override fun sendKeyEvent(event: KeyEvent): Boolean {
            return this@NotifyingEditText.onSoftKeyListener != null && this@NotifyingEditText.onSoftKeyListener!!.onKey(this@NotifyingEditText, event.keyCode, event) || super.sendKeyEvent(event)
        }
    }

    interface PasteListener {
        fun onTextPaste()
    }
}