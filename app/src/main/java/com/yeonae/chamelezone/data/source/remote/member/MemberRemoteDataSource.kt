package com.yeonae.chamelezone.data.source.remote.member

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.network.model.MemberResponse

interface MemberRemoteDataSource {
    fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<String>
    )

    fun login(email: String, password: String, callBack: MemberCallBack<MemberResponse>)

    fun updateMember(
        memberNumber: Int,
        password: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<String>
    )

    fun deleteMember(memberNumber: Int, callBack: MemberCallBack<String>)
}