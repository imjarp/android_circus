package com.example.imjarp.androidcircus.kotlin.generics

class Generic<T>(val myObject: T) {

}

class GenericWithTwo<T, R>(val myTObject: T, val myRObject: R)

open class BaseUser(val name: String = "") {

    fun getUserGreeting() = "Hi $name"
}

open class FacebookUser(val facebookId: String = "", name: String = "") : BaseUser(name) {

    fun getListOfFriends(): List<FacebookUser> = emptyList()
}

class WhatsAppUser(val whatsAppId: String = "", facebookId: String = " ", name: String = " ") : FacebookUser(name, facebookId) {
    fun getListOfContacts(): List<WhatsAppUser> = emptyList()
}


interface UserFinder< T : BaseUser> {

    fun findUser(): T

}

interface UserCompare<in T> {

    fun compare(first: T, second: T): Int
}


