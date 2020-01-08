package com.yeonae.chamelezone.data.repository.member

import com.yeonae.chamelezone.data.source.remote.member.MemberRemoteDataSource

class MemberRepositoryImpl private constructor(private val remoteDataSource: MemberRemoteDataSource) :
    MemberRepository {

    override fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack
    ) {
        remoteDataSource.createMember(email, password, name, nickName, phone, callBack)
    }

    override fun getMember() {

    }

    override fun deleteMember() {

    }

    override fun updateMember() {

    }

    companion object {
        fun getInstance(remoteDataSource: MemberRemoteDataSource): MemberRepository =
            MemberRepositoryImpl(remoteDataSource)
    }
}