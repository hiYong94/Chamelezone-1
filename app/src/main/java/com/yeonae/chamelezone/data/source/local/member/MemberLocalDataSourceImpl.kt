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
    override fun deleteAll(callBack: MemberCallBack<Boolean>) {
        appExecutors.diskIO.execute {
            if (userDatabase.userDao().getUserCount() == 1) {
                val deletedCount = userDatabase.userDao().deleteUser()
                if (deletedCount == 1) {
                    appExecutors.mainThread.execute {
                        callBack.onSuccess(true)
                    }
                } else {
                    callBack.onFailure("삭제 실패")
                }
            } else {
                appExecutors.mainThread.execute {
                    callBack.onSuccess(true)
                }
            }
        }
    }

    override fun loggedLogin(response: MemberResponse, callBack: MemberCallBack<Boolean>) {
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
                    callBack.onSuccess(true)
                }
            }
        }
    }

    override fun logout(callBack: MemberCallBack<String>) {
        appExecutors.diskIO.execute {
            val deletedCount = userDatabase.userDao().deleteUser()
            if (deletedCount == 1) {
                appExecutors.mainThread.execute {
                    callBack.onSuccess("로그아웃 성공")
                }
            } else {
                callBack.onSuccess("로그아웃 실패")
            }
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

    override fun getMember(callBack: MemberCallBack<UserEntity>) {
        appExecutors.diskIO.execute {
            if (userDatabase.userDao().getUserCount() == 1) {
                val user = userDatabase.userDao().getUser()
                appExecutors.mainThread.execute {
                    callBack.onSuccess(user)
                }
            }
        }
    }

    override fun updateMember(nickname: String, phone: String, callBack: MemberCallBack<Boolean>) {
        appExecutors.diskIO.execute {
            val updateCount = userDatabase.userDao().updateUser(nickname, phone)
            Log.d("updateMember1", updateCount.toString())
            if (updateCount == 1) {
                appExecutors.mainThread.execute {
                    callBack.onSuccess(true)
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