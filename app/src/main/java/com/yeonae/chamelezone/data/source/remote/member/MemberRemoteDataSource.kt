package com.yeonae.chamelezone.data.source.remote.member

interface MemberRemoteDataSource {
    fun createMember(email:String, password:String, name:String, nickname:String, phone:String)
    fun getMember()
    fun deleteMember()
    fun updateMember()
}