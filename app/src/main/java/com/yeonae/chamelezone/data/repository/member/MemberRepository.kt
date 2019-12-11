package com.yeonae.chamelezone.data.repository.member

interface MemberRepository {
    fun createMember()
    fun getMember()
    fun deleteMember()
    fun updateMember()
}