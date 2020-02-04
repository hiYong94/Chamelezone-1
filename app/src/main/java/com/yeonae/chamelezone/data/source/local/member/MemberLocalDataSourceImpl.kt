package com.yeonae.chamelezone.data.source.local.member

import android.util.Log
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.room.database.UserDatabase
import com.yeonae.chamelezone.network.room.entity.UserEntity

class MemberLocalDataSourceImpl(private val userDatabase: UserDatabase) : MemberLocalDataSource {
    override fun loggedLogin(response: MemberResponse) {
        val r = Runnable {
            val newUser = UserEntity(
                userNumber = response.memberNumber,
                email = response.email,
                name = response.name,
                nickname = response.nickName,
                phone = response.phoneNumber
            )
            userDatabase.userDao().insertUser(newUser)
            Log.d("MyCall", response.email)
            Log.d("MyCall", userDatabase.userDao().getUser().toString())
        }
        val thread = Thread(r)
        thread.start()
    }

    override fun logout(callBack: MemberCallBack<String>) {
        val r = Runnable {
            userDatabase.userDao().deleteUser()
        }
        val thread = Thread(r)
        thread.start()
        callBack.onSuccess("로그아웃 성공")
    }

    override fun isLogged(callBack: MemberCallBack<Boolean>): Boolean {
        return if (userDatabase.userDao().getUserCount() == 1) {
            callBack.onSuccess(true)
            true
        } else {
            callBack.onSuccess(false)
            false
        }
    }

    companion object {
        fun getInstance(userDatabase: UserDatabase): MemberLocalDataSource =
            MemberLocalDataSourceImpl(userDatabase)
    }
}