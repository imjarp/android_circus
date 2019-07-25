package com.example.imjarp.androidcircus.coroutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.imjarp.androidcircus.utils.IDispatcherApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseScope(protected val dispatcherApp: IDispatcherApp) : CoroutineScope, LifecycleObserver {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + dispatcherApp.getUIDispatcher()


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startJobCreating() {
        job = Job().also {

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun destroyJob() {
        job.cancel()
    }
}