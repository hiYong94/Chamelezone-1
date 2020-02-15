package com.yeonae.chamelezone.view.like.presenter

interface LikeContract {
    interface View {
        var presenter: Presenter
        fun showResultView(response: Boolean)
    }

    interface Presenter {
        fun checkLogin()
    }
}