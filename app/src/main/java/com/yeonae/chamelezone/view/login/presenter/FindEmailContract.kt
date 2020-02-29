package com.yeonae.chamelezone.view.login.presenter

import com.yeonae.chamelezone.network.model.EmailResponse
import com.yeonae.chamelezone.network.model.MemberResponse

interface FindEmailContract {

    interface View {
        var presenter: Presenter
        fun showUserInfo(response: List<EmailResponse>)
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