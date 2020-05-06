package tk.yeonaeyong.shopinshop.view.login.presenter

interface LoginContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
        fun showDialog(message: String)
    }

    interface Presenter {
        fun userLogin(
            email: String,
            password: String
        )
    }
}