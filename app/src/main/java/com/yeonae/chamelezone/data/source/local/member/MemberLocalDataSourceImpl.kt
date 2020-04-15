package com.yeonae.chamelezone.data.source.local.member

import android.util.Log
import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.room.database.UserDatabase
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.util.AppExecutors

class MemberLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val userDatabase: UserDatabase
) : MemberLocalDataSource {
    override fun deleteAll(callback: MemberCallback<Boolean>) {
        appExecutors.diskIO.execute {
            if (userDatabase.userDao().getUserCount() == 1) {
                val deletedCount = userDatabase.userDao().deleteUser()
                if (deletedCount == 1) {
                    appExecutors.mainThread.execute {
                        callback.onSuccess(true)
                    }
                } else {
                    callback.onFailure("삭제 실패")
                }
            } else {
                appExecutors.mainThread.execute {
                    callback.onSuccess(true)
                }
            }
        }
    }

    override fun loggedLogin(response: MemberResponse, callback: MemberCallback<Boolean>) {
        appExecutors.diskIO.execute {
            val newUser = UserEntity(
                userNumber = response.memberNumber,
                email = response.email,
                name = response.name,
                nickname = response.nickName,
                phone = response.phoneNumber
            )
            val insertedPk = userDatabase.userDao().insertUser(newUser)
            if (insertedPk == 0L) {
                appExecutors.mainThread.execute {
                    callback.onSuccess(true)
                }
            }
        }
    }

    override fun logout(callback: MemberCallback<String>) {
        appExecutors.diskIO.execute {
            val deletedCount = userDatabase.userDao().deleteUser()
            if (deletedCount == 1) {
                appExecutors.mainThread.execute {
                    callback.onSuccess("로그아웃 성공")
                }
            } else {
                callback.onSuccess("로그아웃 실패")
            }
        }
    }

    override fun isLogged(callback: MemberCallback<Boolean>) {
        appExecutors.diskIO.execute {
            if (userDatabase.userDao().getUserCount() == 1) {
                appExecutors.mainThread.execute {
                    callback.onSuccess(true)
                }
            } else {
                appExecutors.mainThread.execute {
                    callback.onSuccess(false)
                }
            }
        }
    }

    override fun getMember(callback: MemberCallback<UserEntity>) {
        appExecutors.diskIO.execute {
            if (userDatabase.userDao().getUserCount() == 1) {
                val user = userDatabase.userDao().getUser()
                appExecutors.mainThread.execute {
                    callback.onSuccess(user)
                }
            } else{
                callback.onFailure("없음")
            }
        }
    }

    override fun updateMember(nickname: String, phone: String, callback: MemberCallback<Boolean>) {
        appExecutors.diskIO.execute {
            val updateCount = userDatabase.userDao().updateUser(nickname, phone)
            Log.d("updateMember1", updateCount.toString())
            if (updateCount == 1) {
                appExecutors.mainThread.execute {
                    callback.onSuccess(true)
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