package com.yeonae.chamelezone.data.repository.member

interface MemberRepository {
    fun createMember(email:String, password:String, name:String, nickname:String, phone:String)
    fun getMember()
    fun deleteMember()
    fun updateMember()
}