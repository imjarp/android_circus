package com.example.imjarp.androidcircus

import com.example.imjarp.androidcircus.kotlin.generics.BaseUser
import com.example.imjarp.androidcircus.kotlin.generics.FacebookUser
import com.example.imjarp.androidcircus.kotlin.generics.WhatsAppUser
import org.junit.Test

class TestGeneric {

    @Test
    fun testGeneric() {


        val list = listOf(WhatsAppUser(), WhatsAppUser())

        if( list is List<FacebookUser>){
            println("Soy")
        }
                /*val whatsappUserFinder = object : UserFinder<WhatsAppUser> {
            override fun findUser(): WhatsAppUser {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }*/


        val userList = mutableListOf<BaseUser>()
        userList.add(FacebookUser())
        userList.add(WhatsAppUser())


    }
}