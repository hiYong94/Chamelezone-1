package com.yeonae.chamelezone.data.repository.member

import android.os.Message
import android.util.Log
import com.yeonae.chamelezone.data.source.local.member.MemberLocalDataSource
import com.yeonae.chamelezone.data.source.remote.member.MemberRemoteDataSource
import com.yeonae.chamelezone.network.model.MemberResponse

class MemberRepositoryImpl private constructor(private val remoteDataSource: MemberRemoteDataSource, private val localDataSource: MemberLocalDataSource) :
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

    override fun login(email: String, password: String, callBack: MemberCallBack<MemberResponse>) {
        remoteDataSource.login(email, password, object : MemberCallBack<MemberResponse>{
            override fun onSuccess(response: MemberResponse) {
                callBack.onSuccess(response)
                localDataSource.loggedLogin(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun getMember() {

    }

    override fun deleteMember() {

    }

    override fun updateMember() {

    }

    companion object {
        fun getInstance(remoteDataSource: MemberRemoteDataSource, localDataSource: MemberLocalDataSource): MemberRepository =
            MemberRepositoryImpl(remoteDataSource, localDataSource)
    }
}