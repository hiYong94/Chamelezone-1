package tk.yeonaeyong.shopinshop.data.source.local.member

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.network.model.MemberResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface MemberLocalDataSource {
    fun loggedLogin(response: MemberResponse, callback: MemberCallback<Boolean>)
    fun logout(callback: MemberCallback<String>)
    fun isLogged(callback: MemberCallback<Boolean>)
    fun deleteAll(callback: MemberCallback<Boolean>)
    fun getMember(callback: MemberCallback<UserEntity>)
    fun updateMember(nickname: String, phone: String, callback: MemberCallback<Boolean>)
}