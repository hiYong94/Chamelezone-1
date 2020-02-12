package com.yeonae.chamelezone.view.mypage.presenter

import com.yeonae.chamelezone.network.room.entity.UserEntity

interface MypageContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
        fun showResultView(response: Boolean)
        fun showUserInfo(user: UserEntity)
    }

    interface Presenter {
        fun logout()
        fun checkLogin()
        fun getUser()
    }
}