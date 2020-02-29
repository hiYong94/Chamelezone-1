package com.yeonae.chamelezone.view.login.presenter

import com.yeonae.chamelezone.network.model.EmailResponse
import com.yeonae.chamelezone.network.model.NicknameResponse

interface JoinContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
        fun showEmailMessage(response: EmailResponse)
        fun showNicknameMessage(response: NicknameResponse)
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