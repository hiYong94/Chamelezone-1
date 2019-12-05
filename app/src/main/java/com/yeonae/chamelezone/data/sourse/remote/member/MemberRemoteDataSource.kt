package com.yeonae.chamelezone.data.sourse.remote.member

interface MemberRemoteDataSource {
    fun createMember()
    fun getMember()
    fun deleteMember()
    fun updateMember()
}