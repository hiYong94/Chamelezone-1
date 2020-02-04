package com.yeonae.chamelezone.view.mypage.presenter

interface MypageContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
    }

    interface Presenter {
        fun logout()
    }
}