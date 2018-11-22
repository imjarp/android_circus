package com.example.imjarp.androidcircus.utils

import kotlinx.coroutines.Dispatchers

class DisptachersApp : IDispatcherApp {

    override fun getUIDispatcher() = Dispatchers.Main
    override fun getIODispatcher() = Dispatchers.IO


}