package com.example.imjarp.androidcircus.utils

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatcherApp {

    fun getUIDispatcher() : CoroutineDispatcher

    fun getIODispatcher() : CoroutineDispatcher

}