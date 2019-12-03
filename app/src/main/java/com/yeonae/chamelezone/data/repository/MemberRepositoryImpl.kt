package com.yeonae.chamelezone.data.repository

import com.yeonae.chamelezone.data.sourse.remote.MemberRemoteDataSource

class MemberRepositoryImpl private constructor(  // 싱글턴으로만 접근할 수 있도록
    private val remoteDataSource: MemberRemoteDataSource
) : MemberRepository {
    override fun createMember() {
        remoteDataSource.createMember()
    }

    override fun getMember() {
        remoteDataSource.getMember()
    }

    override fun deleteMember() {
        remoteDataSource.deleteMember()
    }

    override fun updateMember() {
        remoteDataSource.updateMember()
    }

    companion object{
        fun getInstence(remoteDataSource: MemberRemoteDataSource) : MemberRepository = MemberRepositoryImpl(remoteDataSource)
    }
}