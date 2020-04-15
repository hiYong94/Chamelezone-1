package com.yeonae.chamelezone.data.source.remote.member

import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.network.model.*

interface MemberRemoteDataSource {
    fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callback: MemberCallback<String>
    )

    fun login(email: String, password: String, callback: MemberCallback<MemberResponse>)

    fun updateMember(
        memberNumber: Int,
        password: String?,
        nickName: String,
        phone: String,
        callback: MemberCallback<Boolean>
    )

    fun deleteMember(memberNumber: Int, callback: MemberCallback<String>)

    fun checkEmail(email: String, callback: MemberCallback<EmailResponse>)

    fun checkNickname(nickname: String, callback: MemberCallback<NicknameResponse>)

    fun findEmail(name: String, phone: String, callback: MemberCallback<List<EmailResponse>>)

    fun findPassword(email: String, phone: String, callback: MemberCallback<FindPasswordResponse>)

    fun checkSecurityCode(
        securityCode: String,
        email: String,
        phone: String,
        callback: MemberCallback<SecurityCodeResponse>
    )

    fun changePassword(password: String, memberNumber: Int, callback: MemberCallback<Boolean>)
}