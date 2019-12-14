package com.yeonae.chamelezone.data.source.remote.member

class MemberRemoteDataSourceImpl private constructor(/*api: Api*/) :
    MemberRemoteDataSource {
    override fun createMember() {

    }

    override fun getMember() {

    }

    override fun deleteMember() {

    }

    override fun updateMember() {

    }

    companion object {
        fun getInstance(): MemberRemoteDataSource = MemberRemoteDataSourceImpl()
    }
}