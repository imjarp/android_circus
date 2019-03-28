package com.example.imjarp.androidcircus.kotlin

import android.view.View
import android.widget.Button
import android.widget.Toast

//Functions extensions
fun String?.countChar(char: Char): Int {
    if (this == null) return -1
    return this.count { ch -> ch == char }
}

// Top level functions
fun countChars(word: String, char: Char): Int {
    return word.countChar(char)
}


object WordCounter {
    // no constructors
    fun countChard(word: String, char: Char): Int {
        return word.countChar(char)
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return this.hashCode()
    }

}

