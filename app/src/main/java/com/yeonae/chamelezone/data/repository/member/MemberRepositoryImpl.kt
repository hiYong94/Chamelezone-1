package com.yeonae.chamelezone.data.repository.member

import com.yeonae.chamelezone.data.source.local.member.MemberLocalDataSource
import com.yeonae.chamelezone.data.source.remote.member.MemberRemoteDataSource
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class MemberRepositoryImpl private constructor(
    private val remoteDataSource: MemberRemoteDataSource,
    private val localDataSource: MemberLocalDataSource
) :
    MemberRepository {
    override fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<String>
    ) {
        remoteDataSource.createMember(email, password, name, nickName, phone, callBack)
    }

    override fun getMember(callBack: MemberCallBack<UserEntity>) {
        localDataSource.getMember(callBack)
    }

    override fun login(
        email: String,
        password: String,
        callBack: MemberCallBack<MemberResponse>,
        localCallBack: MemberCallBack<Boolean>
    ) {
        remoteDataSource.login(email, password, object : MemberCallBack<MemberResponse> {
            override fun onSuccess(response: MemberResponse) {
                callBack.onSuccess(response)
                localDataSource.loggedLogin(response, localCallBack)
            }

            override fun onFailure(message: String) {
                callBack.onFailure(message)
            }
        })
    }

    override fun logout(callBack: MemberCallBack<String>) {
        localDataSource.logout(callBack)
    }

    override fun updateMember(
        memberNumber: Int,
        password: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<Boolean>,
        localCallBack: MemberCallBack<Boolean>
    ) {
        remoteDataSource.updateMember(
            memberNumber,
            password,
            nickName,
            phone,
            object : MemberCallBack<Boolean> {
                override fun onSuccess(response: Boolean) {
                    callBack.onSuccess(response)
                    if (response) {
                        localDataSource.updateMember(nickName, phone, localCallBack)
                    }
                }

                override fun onFailure(message: String) {
                    callBack.onFailure(message)
                }

            })
    }

    override fun deleteMember(memberNumber: Int, callBack: MemberCallBack<String>) {
        remoteDataSource.deleteMember(memberNumber, callBack)
    }

    override fun checkLogin(callBack: MemberCallBack<Boolean>) {
        localDataSource.isLogged(callBack)
    }

    override fun deleteLoginUser(callBack: MemberCallBack<Boolean>) {
        localDataSource.deleteAll(callBack)
    }

    override fun checkEmail(email: String, callBack: MemberCallBack<Boolean>) {
        remoteDataSource.checkEmail(email, callBack)
    }

    override fun checkNickname(nickname: String, callBack: MemberCallBack<Boolean>) {
        remoteDataSource.checkNickname(nickname, callBack)
    }

    companion object {
        fun getInstance(
            remoteDataSource: MemberRemoteDataSource,
            localDataSource: MemberLocalDataSource
        ): MemberRepository =
            MemberRepositoryImpl(remoteDataSource, localDataSource)
    }
}