package com.yeonae.chamelezone.data.sourse.remote

interface MemberRemoteDataSource {
    fun createMember()
    fun getMember()
    fun deleteMember()
    fun updateMember()
}