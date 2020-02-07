package com.yeonae.chamelezone.data.repository.member

import com.yeonae.chamelezone.data.source.local.member.MemberLocalDataSource
import com.yeonae.chamelezone.data.source.remote.member.MemberRemoteDataSource
import com.yeonae.chamelezone.network.model.MemberResponse

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

    override fun getMember(
        email: String,
        password: String,
        callBack: MemberCallBack<MemberResponse>,
        localCallBack: MemberCallBack<Boolean>
    ) {
        remoteDataSource.getMember(email, password, object : MemberCallBack<MemberResponse> {
            override fun onSuccess(response: MemberResponse) {
                callBack.onSuccess(response)
                localDataSource.loggedLogin(response, localCallBack)
            }

            override fun onFailure(message: String) {

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
        callBack: MemberCallBack<String>
    ) {
        remoteDataSource.updateMember(memberNumber, password, nickName, phone, callBack)
    }

    override fun deleteMember(memberNumber: Int, callBack: MemberCallBack<String>) {
        remoteDataSource.deleteMember(memberNumber, callBack)
    }

    override fun checkLogin(callBack: MemberCallBack<Boolean>) {
        localDataSource.isLogged(callBack)
    }

    companion object {
        fun getInstance(
            remoteDataSource: MemberRemoteDataSource,
            localDataSource: MemberLocalDataSource
        ): MemberRepository =
            MemberRepositoryImpl(remoteDataSource, localDataSource)
    }
}