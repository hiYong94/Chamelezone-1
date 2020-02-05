package com.yeonae.chamelezone.data.source.local.member

import android.util.Log
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.room.database.UserDatabase
import com.yeonae.chamelezone.network.room.entity.User

class MemberLocalDataSourceImpl(private val userDatabase: UserDatabase) : MemberLocalDataSource {

    override fun loggedLogin(response: MemberResponse) {
        val r = Runnable {
            val newUser = User(
                response.memberNumber,
                response.email,
                response.name,
                response.nickName,
                response.phoneNumber
            )
            userDatabase?.userDao()?.insertUser(newUser)
            Log.d("MyCall", response.email)
            Log.d("MyCall", userDatabase?.userDao()?.getUser().toString())
        }
        val thread = Thread(r)
        thread.start()
    }

    companion object {
        fun getInstance(userDatabase: UserDatabase): MemberLocalDataSource =
            MemberLocalDataSourceImpl(userDatabase)
    }
}