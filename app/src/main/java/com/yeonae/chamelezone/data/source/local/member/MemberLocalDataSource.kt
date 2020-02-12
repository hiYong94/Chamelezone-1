package com.yeonae.chamelezone.data.source.local.member

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface MemberLocalDataSource {
    fun loggedLogin(memberResponse: MemberResponse, callBack: MemberCallBack<Boolean>)
    fun logout(callBack: MemberCallBack<String>)
    fun isLogged(callBack: MemberCallBack<Boolean>)
    fun deleteAll(callBack: MemberCallBack<Boolean>)
    fun getMember(callBack: MemberCallBack<UserEntity>)
}