package com.example.imjarp.androidcircus.coroutine

import android.util.Log
import com.example.imjarp.androidcircus.utils.IDispatcherApp
import kotlinx.coroutines.*

class UrlPresenter(dispatcherApp: IDispatcherApp, private val listener: UrlPresenterMethods) : BaseScope(dispatcherApp) {



    fun fetchUrl(firstUrl: String, secondUrl: String) = launch(context = dispatcherApp.getIODispatcher()) {

        //main thread
        val threadName = Thread.currentThread().name

        val one = async {
            "1" + executeUrl(firstUrl)
        }

        val two = async {
            "2" + executeUrl(secondUrl)
        }

        val result = one.await() + two.await()

        processResult(result)

    }

    fun execute() = launch {
        //We are on the main thread
        listener.showText("Loading")
        withContext(dispatcherApp.getIODispatcher()) {
            Thread.sleep(10_000)
            if(isActive) {
                val result = executeUrl("http://www.example.com/")
                processResult(result)
            }else{
                Log.d("workshop", "I was cancelled")
            }

        }
    }

    private suspend fun processResult(result: String) {
        withContext(dispatcherApp.getUIDispatcher()) {
            if (isActive) {
                Log.d("workshop", "Process Result $result")
                listener.showText(result)
            }
        }
    }


    private fun executeUrl(url: String): String {
        return java.net.URL(url).readText().subSequence(0, 10) as String
    }

    interface UrlPresenterMethods {
        fun showText(text: String)
    }

}