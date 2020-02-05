package com.yeonae.chamelezone.view.mypage.presenter

interface MypageContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
        fun showLoginView(response: Boolean)
    }

    interface Presenter {
        fun logout()
        fun checkLogin()
    }
}