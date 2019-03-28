package com.example.imjarp.androidcircus.kotlin

class Singleton {

    private constructor() {

    }


    companion object {

        private var myInstance: Singleton? = null
        @JvmStatic
        fun getInstance(): Singleton {
            synchronized(this){
                if (myInstance == null) myInstance = Singleton()
            }

            return myInstance!!

        }
    }
}