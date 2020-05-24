package tk.yeonaeyong.shopinshop.view.login.presenter

import tk.yeonaeyong.shopinshop.network.model.EmailSendResultResponse

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