package tk.yeonaeyong.shopinshop.view.home.presenter

interface StartContract {
    interface View {
        var presenter: Presenter
        fun showMessage(isSuccess: Boolean)
        fun showDialog(message: String)
        fun showAppUpdateDialog(version: String)
    }

    interface Presenter {
        fun userLogin(
            email: String,
            password: String
        )

        fun logout()
        fun getAppVersion()
    }
}