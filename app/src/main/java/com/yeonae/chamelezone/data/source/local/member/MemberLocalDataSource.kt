package com.yeonae.chamelezone.data.source.local.member

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.network.model.MemberResponse

interface MemberLocalDataSource {
    fun loggedLogin(memberResponse: MemberResponse)
    fun logout(callBack: MemberCallBack<String>)
    fun isLogged(callBack: MemberCallBack<Boolean>)
}