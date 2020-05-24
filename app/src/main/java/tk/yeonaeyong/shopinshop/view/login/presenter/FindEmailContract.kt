package tk.yeonaeyong.shopinshop.view.login.presenter

interface FindEmailContract {

    interface View {
        var presenter: Presenter
        fun showUserInfo(emails: ArrayList<String>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun findEmail(
            name: String,
            phone: String
        )
    }
}