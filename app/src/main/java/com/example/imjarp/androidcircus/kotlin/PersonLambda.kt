package com.example.imjarp.androidcircus.kotlin

import android.app.Notification
import android.content.Intent
import android.net.Uri

class PersonLambda(val name: String, val age: Int) {


    private var countVar = 1
    // Lambdas treat functions as values
    private val countAChars = { rp: PersonLambda ->
        WordCounter.countChard(rp.name, 'A')
    }

    val isAdult = { p: PersonLambda ->
        p.age > 18
    }

    fun count(anyFunction: (PersonLambda) -> Int): Int {

        countVar++
        val num = countChars("A", 'A');
        val charsWithA = countAChars.invoke(this)
        return anyFunction(this)

    }

    fun maxAge(persons: List<PersonLambda>): PersonLambda? {

        // 1st
        var p: PersonLambda? = null
        var maxAge = 0
        for (person in persons) {
            if (person.age > maxAge) {
                p = person
                maxAge = person.age
            }
        }
        // 2nd
        p = persons.maxBy(PersonLambda::age)

        // 3rd uses lambda
        val adults = persons.all(isAdult)








        val generic = persons.filterInlined {
            it.name.startsWith("A")
        }
        return p
    }

    inline fun List<PersonLambda>.filterInlined(filterBlock: (p: PersonLambda) -> Boolean): List<PersonLambda> {
        val list = mutableListOf<PersonLambda>()
        this.forEach {
            if (filterBlock(it)) {
                list.add(it)
            }
        }
        return list
    }
}


