package com.yeonae.chamelezone.data.repository.member

interface MemberRepository {
    fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack
    )

    fun login(email: String, password: String, callBack: MemberCallBack)
    fun getMember()
    fun deleteMember()
    fun updateMember()
}


