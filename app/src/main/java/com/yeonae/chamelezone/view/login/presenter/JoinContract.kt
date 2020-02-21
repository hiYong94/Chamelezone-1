package com.yeonae.chamelezone.view.login.presenter

interface JoinContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
        fun showEmailMessage(response: Boolean)
        fun showNicknameMessage(response: Boolean)
    }

    interface Presenter {
        fun userRegister(
            email: String,
            password: String,
            name: String,
            nickName: String,
            phone: String
        )

        fun checkEmail(email: String)
        fun checkNickname(nickName: String)
    }
}