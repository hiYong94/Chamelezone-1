package com.yeonae.chamelezone.data.repository.member

import com.yeonae.chamelezone.data.sourse.remote.member.MemberRemoteDataSource

class MemberRepositoryImpl private constructor(private val remoteDataSource: MemberRemoteDataSource) :
    MemberRepository {
    override fun createMember() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMember() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteMember() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateMember() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun getInstance(remoteDataSource: MemberRemoteDataSource): MemberRepository =
            MemberRepositoryImpl(remoteDataSource)
    }
}