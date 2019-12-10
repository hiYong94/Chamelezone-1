package com.yeonae.chamelezone.data.repositroy.member

interface MemberRepository {
    fun createMember()
    fun getMember()
    fun deleteMember()
    fun updateMember()
}