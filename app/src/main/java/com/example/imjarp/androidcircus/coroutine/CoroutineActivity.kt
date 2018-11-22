package com.example.imjarp.androidcircus.coroutine

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.imjarp.androidcircus.R
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CoroutineActivity : AppCompatActivity() {

    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        setSupportActionBar(toolbar)

        textView = findViewById(R.id.result_text)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            simpleCallLaunch()
        }
    }

    private fun simpleCallNetwork() {

        textView?.text = java.net.URL("http://github.com/imjarp").readText()
    }

    private fun simpleCallLaunch() {


        GlobalScope.launch {
            Thread.sleep(2_000)
            var thread = "First i was in ${Thread.currentThread().name}"
            val response = java.net.URL("http://www.example.com/").readText()

            textView?.post {
                thread += "\n Then i was in ${Thread.currentThread().name}"
                textView?.text = thread + "\n " + response.subSequence(0, 10)
            }

        }

        textView?.text = "Before dissapear"

    }

    private suspend fun postSequence() {


    }


}
