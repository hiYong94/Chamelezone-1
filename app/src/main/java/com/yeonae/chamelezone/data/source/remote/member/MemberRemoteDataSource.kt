package com.yeonae.chamelezone.data.source.remote.member

import com.yeonae.chamelezone.data.repository.member.MemberCallBack

interface MemberRemoteDataSource {
    fun createMember(email:String, password:String, name:String, nickName:String, phone:String, callBack: MemberCallBack)
    fun getMember()
    fun deleteMember()
    fun updateMember()
}