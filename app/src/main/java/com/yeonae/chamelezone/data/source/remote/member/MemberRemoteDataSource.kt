package com.yeonae.chamelezone.data.source.remote.member

interface MemberRemoteDataSource {
    fun createMember()
    fun getMember()
    fun deleteMember()
    fun updateMember()
}