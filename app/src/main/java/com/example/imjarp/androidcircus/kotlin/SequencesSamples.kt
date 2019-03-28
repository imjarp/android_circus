package com.example.imjarp.androidcircus.kotlin

class SequencesSamples {


    fun filter(numbers: MutableList<Int>) {

        numbers.addAll(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

        val y = numbers.asSequence()
                .filter {
                    it % 2 == 0
                }.map {
                    it * 2
                }

        var intermediate = y.toList()


        numbers.addAll(listOf(20, 21, 22, 23, 24, 25, 26))


        intermediate = y.toList()


        numbers.remove(4)
        numbers.remove(8)

        intermediate = y.toList()

        print(intermediate)
    }


}