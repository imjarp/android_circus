package com.example.imjarp.androidcircus.coroutine

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.example.imjarp.androidcircus.R
import com.example.imjarp.androidcircus.utils.DisptachersApp
import com.example.imjarp.androidcircus.utils.IDispatcherApp

import kotlinx.android.synthetic.main.activity_example_presenter_actitivty.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ExamplePresenterActivity : AppCompatActivity(), UrlPresenter.UrlPresenterMethods {

    private var urlPresenter: UrlPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_presenter_actitivty)
        setSupportActionBar(toolbar)
        urlPresenter = UrlPresenter(DisptachersApp(), this)
        lifecycle.addObserver(urlPresenter!!)

        findViewById<View>(R.id.btn).setOnClickListener {
            //urlPresenter?.execute()
            urlPresenter?.fetchUrl("www.example.com","http://www.google.com")
        }

    }

    override fun onPause() {
        super.onPause()
        urlPresenter?.let {
            lifecycle.removeObserver(it)
            urlPresenter = null
        }

    }

    override fun showText(text: String) {
        findViewById<TextView>(R.id.content_text).text = text
    }


}
