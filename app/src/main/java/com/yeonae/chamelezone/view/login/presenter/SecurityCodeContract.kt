package com.yeonae.chamelezone.view.login.presenter

interface SecurityCodeContract {

    interface View {
        var presenter: Presenter
        fun showResultView(matchResult: Boolean)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun checkSecurityCode(
            securityCode: String,
            email: String,
            phone: String
        )
    }
}