package com.yeonae.chamelezone.data.source.local.member

import android.util.Log
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.room.database.UserDatabase
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.util.AppExecutors

class MemberLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val userDatabase: UserDatabase
) : MemberLocalDataSource {
    override fun loggedLogin(response: MemberResponse) {
        appExecutors.diskIO.execute {
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
            appExecutors.mainThread.execute {
            }
        }
    }

    override fun logout(callBack: MemberCallBack<String>) {
        appExecutors.diskIO.execute {
            userDatabase.userDao().deleteUser()
            appExecutors.mainThread.execute {
                callBack.onSuccess("로그아웃 성공")
            }
            Log.d("MyCall", userDatabase.userDao().getUserCount().toString())
        }
    }

    override fun isLogged(callBack: MemberCallBack<Boolean>) {
        appExecutors.diskIO.execute {
            if (userDatabase.userDao().getUserCount() == 1) {
                appExecutors.mainThread.execute {
                    callBack.onSuccess(true)
                }
            } else {
                appExecutors.mainThread.execute {
                    callBack.onSuccess(false)
                }
            }
        }
    }

    companion object {
        fun getInstance(
            appExecutors: AppExecutors,
            userDatabase: UserDatabase
        ): MemberLocalDataSource =
            MemberLocalDataSourceImpl(appExecutors, userDatabase)
    }
}