package com.yeonae.chamelezone.data.repository.member

import com.yeonae.chamelezone.data.source.remote.member.MemberRemoteDataSource

class MemberRepositoryImpl private constructor(private val remoteDataSource: MemberRemoteDataSource) :
    MemberRepository {
    override fun createMember() {
        remoteDataSource.createMember()
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