package com.example.imjarp.androidcircus.kotlin

import android.util.Log
import android.view.View

class KotlinSamples {


    //Simulate user
    class FacebookUser() : User {

        var count = 1
        override val nickname: String
            get() {
                return getFromFacebook()
            }

        fun getFromFacebook(): String {
            return "Marco ${count++}"
        }
    }

    class FixedUser(override val nickname: String = "JARP") : User

    interface User {
        val nickname: String
    }


    // Multiple interface
    fun setClick(view: View) {

        view.setOnClickListener { ClickListener }
        view.setOnLongClickListener(ClickListener)
    }

    object ClickListener : View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            Log.d("TAG", "I was clicked")
        }

        override fun onLongClick(v: View?): Boolean {
            Log.d("TAG", "I was long clicked")
            return true
        }
    }


}