package com.yeonae.chamelezone.view.login.presenter

import com.yeonae.chamelezone.network.model.EmailSendResultResponse

interface FindPasswordContract {

    interface View {
        var presenter: Presenter
        fun deliverUserInfo(response: EmailSendResultResponse)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun findPassword(
            email: String,
            phone: String
        )
    }
}