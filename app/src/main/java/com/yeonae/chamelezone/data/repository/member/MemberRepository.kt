package com.yeonae.chamelezone.data.repository.member

import com.yeonae.chamelezone.network.model.*
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface MemberRepository {
    fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<String>
    )

    fun getMember(callBack: MemberCallBack<UserEntity>)

    fun login(
        email: String,
        password: String,
        callBack: MemberCallBack<MemberResponse>,
        localCallBack: MemberCallBack<Boolean>
    )

    fun logout(callBack: MemberCallBack<String>)

    fun updateMember(
        memberNumber: Int,
        password: String?,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<Boolean>,
        localCallBack: MemberCallBack<Boolean>
    )

    fun deleteMember(memberNumber: Int, callBack: MemberCallBack<String>)

    fun checkLogin(callBack: MemberCallBack<Boolean>)

    fun deleteLoginUser(callBack: MemberCallBack<Boolean>)

    fun checkEmail(email: String, callBack: MemberCallBack<EmailResponse>)

    fun checkNickname(nickname: String, callBack: MemberCallBack<NicknameResponse>)

    fun findEmail(name: String, phone: String, callBack: MemberCallBack<List<EmailResponse>>)

    fun findPassword(email: String, phone: String, callBack: MemberCallBack<FindPasswordResponse>)

    fun checkSecurityCode(
        securityCode: String,
        email: String,
        phone: String,
        callBack: MemberCallBack<SecurityCodeResponse>
    )

    fun changePassword(password: String, memberNumber: Int, callBack: MemberCallBack<Boolean>)
}


