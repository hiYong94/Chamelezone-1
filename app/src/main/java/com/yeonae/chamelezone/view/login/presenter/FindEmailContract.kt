package com.yeonae.chamelezone.view.login.presenter

interface FindEmailContract {

    interface View {
        var presenter: Presenter
        fun showUserInfo(emails: ArrayList<String>)
    }

    interface Presenter {
        fun findEmail(
            name: String,
            phone: String
        )

        fun findPassword(
            email: String,
            phone: String
        )
    }
}