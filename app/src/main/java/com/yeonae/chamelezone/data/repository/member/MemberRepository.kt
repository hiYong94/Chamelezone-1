package com.yeonae.chamelezone.data.repository.member

import com.yeonae.chamelezone.network.model.MemberResponse

interface MemberRepository {
    fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<String>
    )

    fun getMember(email: String, password: String, callBack: MemberCallBack<MemberResponse>)

    fun updateMember(
        memberNumber: Int,
        password: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<String>
    )

    fun deleteMember(memberNumber: Int, callBack: MemberCallBack<String>)
}


