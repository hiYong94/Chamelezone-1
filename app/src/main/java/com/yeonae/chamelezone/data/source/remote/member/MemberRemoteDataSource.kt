package com.yeonae.chamelezone.data.source.remote.member

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.network.model.EmailResponse
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.model.NicknameResponse

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
        password: String?,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<Boolean>
    )

    fun deleteMember(memberNumber: Int, callBack: MemberCallBack<String>)

    fun checkEmail(email: String, callBack: MemberCallBack<EmailResponse>)

    fun checkNickname(nickname: String, callBack: MemberCallBack<NicknameResponse>)

    fun findEmail(name: String, phone: String, callBack: MemberCallBack<List<EmailResponse>>)

    fun findPassword(email: String, phone: String, callBack: MemberCallBack<MemberResponse>)

    fun changePassword(password: String, memberNumber: Int, callBack: MemberCallBack<Boolean>)
}