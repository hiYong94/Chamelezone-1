package com.yeonae.chamelezone.view.mypage.presenter

import com.yeonae.chamelezone.network.model.NicknameResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface UserModifyContract {

    interface View {
        var presenter: Presenter
        fun showUserInfo(user: UserEntity)
        fun showMessage(response: Boolean)
        fun showNicknameMessage(response: NicknameResponse)
    }

    interface Presenter {
        fun getUser()

        fun updateMember(
            memberNumber: Int,
            password: String,
            nickName: String,
            phone: String
        )

        fun checkNickname(nickName: String)
    }
}