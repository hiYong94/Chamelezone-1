package com.yeonae.chamelezone.view.login.presenter

interface JoinContract {

    interface View {
        var presenter: Presenter
        fun join(message: String)
    }

    interface Presenter {
        fun userRegister(
            email: String,
            password: String,
            name: String,
            nickName: String,
            phone: String
        )

        fun userLogin(
            email: String,
            password: String
        )
    }
}