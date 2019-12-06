package com.yeonae.chamelezone.data.repositroy

import com.yeonae.chamelezone.data.sourse.remote.MemberRemoteDataSource

class MemberRepositoryImpl private constructor(val remoteDataSource: MemberRemoteDataSource): MemberRepository{
    override fun createMember() {
        remoteDataSource.createMember()
    }

    override fun getMember() {

    }

    override fun deleteMember() {

    }

    override fun updateMember() {

    }

    companion object{
        fun getInstance(remoteDataSource: MemberRemoteDataSource):MemberRepository = MemberRepositoryImpl(remoteDataSource)
    }
}