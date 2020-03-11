package com.yeonae.chamelezone.view.home.presenter

interface StartContract {
    interface View {
        var presenter: Presenter
        fun showMessage(isSuccess: Boolean)
        fun showDialog(message: String)
    }

    interface Presenter {
        fun userLogin(
            email: String,
            password: String
        )

        fun logout()
    }
}