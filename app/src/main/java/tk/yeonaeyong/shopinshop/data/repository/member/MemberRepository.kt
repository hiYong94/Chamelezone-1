package tk.yeonaeyong.shopinshop.data.repository.member

import tk.yeonaeyong.shopinshop.network.model.*
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface MemberRepository {
    fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callback: MemberCallback<String>
    )

    fun getMember(callback: MemberCallback<UserEntity>)

    fun login(
        email: String,
        password: String,
        callback: MemberCallback<MemberResponse>,
        localCallback: MemberCallback<Boolean>
    )

    fun logout(callback: MemberCallback<String>)

    fun updateMember(
        memberNumber: Int,
        password: String?,
        nickName: String,
        phone: String,
        callback: MemberCallback<Boolean>,
        localCallback: MemberCallback<Boolean>
    )

    fun deleteMember(memberNumber: Int, callback: MemberCallback<String>)

    fun checkLogin(callback: MemberCallback<Boolean>)

    fun deleteLoginUser(callback: MemberCallback<Boolean>)

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


