package com.yeonae.chamelezone.view.home.presenter

interface StartContract {
    interface View {
        var presenter: Presenter
        fun showMessage(isSuccess: Boolean)
    }

    interface Presenter {
        fun logout()
    }
}