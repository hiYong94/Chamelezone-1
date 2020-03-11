package com.yeonae.chamelezone.view.login.presenter

interface FindPasswordContract {

    interface View {
        var presenter: Presenter
        fun deliverUserInfo(memberNumber: Int)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun findPassword(
            email: String,
            phone: String
        )
    }
}