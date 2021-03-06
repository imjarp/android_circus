package com.example.imjarp.androidcircus.coroutine

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.imjarp.androidcircus.R
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor


class CoroutineActivity : AppCompatActivity() {

    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        setSupportActionBar(toolbar)

        textView = findViewById(R.id.result_text)
        textView?.text = "thread name =  ${Thread.currentThread().name}"


        val handlerThread = HandlerThread("Background")

        handlerThread.start()

        val handler = Handler(handlerThread.looper)



        fab.setOnClickListener { view ->
            //simpleCallNetwork()
            //simpleCallLaunch()
            //launchTwoOperationsAsync()

            startActivity(Intent(this, ExamplePresenterActivity::class.java))
        }
    }

    private fun simpleCallNetwork() {

        textView?.text = java.net.URL("http://github.com/imjarp").readText()
    }

    private fun simpleCallLaunch() {

        // This will be limited to the end of application
        GlobalScope.launch {
            Thread.sleep(6_000)
            var thread = "First job thread name =  ${Thread.currentThread().name}"
            val response = java.net.URL("http://www.example.com/").readText().subSequence(0, 20) as String

            Log.d("workshop", response)
            textView?.post {
                thread += "\n Then i was in ${Thread.currentThread().name}" + response
                textView?.text = thread
            }

        }

        textView?.text = "Before dissapear"

    }

    private fun launchTwoOperationsAsync() {



        GlobalScope.launch {
            val j1 = GlobalScope.async {

                Thread.sleep(10_000)
                "J1" + executeUrl("http://www.example.com/")
            }


            val j2 = GlobalScope.async {
                "J2" + executeUrl("http://www.google.com/")
            }

            val result = j1.await() + j2.await()

            withContext(Dispatchers.Main) {
                textView?.text = result
            }

        }

        textView?.text = "Before dissapear"

    }

    private fun executeUrl(url: String): String {
        return java.net.URL(url).readText().subSequence(0, 10) as String
    }


}
