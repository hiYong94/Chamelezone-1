package com.yeonae.chamelezone.data.sourse.member.remote

interface MemberRemoteDataSource {
    fun createMember()
    fun getMember()
    fun deleteMember()
    fun updateMember()
}