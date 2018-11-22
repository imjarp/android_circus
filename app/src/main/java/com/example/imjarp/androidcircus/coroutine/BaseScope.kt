package com.example.imjarp.androidcircus.coroutine

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class BaseScope : CoroutineScope, LifecycleObserver {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startJobCreating(){
        job = Job()
    }
}