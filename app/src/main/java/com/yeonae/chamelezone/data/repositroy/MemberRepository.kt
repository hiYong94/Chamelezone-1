package com.yeonae.chamelezone.data.repositroy

interface MemberRepository {
    fun createMember()
    fun getMember()
    fun deleteMember()
    fun updateMember()
}