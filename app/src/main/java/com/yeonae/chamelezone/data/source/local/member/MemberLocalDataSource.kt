package com.yeonae.chamelezone.data.source.local.member

import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface MemberLocalDataSource {
    fun loggedLogin(response: MemberResponse, callback: MemberCallback<Boolean>)
    fun logout(callback: MemberCallback<String>)
    fun isLogged(callback: MemberCallback<Boolean>)
    fun deleteAll(callback: MemberCallback<Boolean>)
    fun getMember(callback: MemberCallback<UserEntity>)
    fun updateMember(nickname: String, phone: String, callback: MemberCallback<Boolean>)
}