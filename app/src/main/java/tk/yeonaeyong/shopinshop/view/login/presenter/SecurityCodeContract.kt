package tk.yeonaeyong.shopinshop.view.login.presenter

interface SecurityCodeContract {

    interface View {
        var presenter: Presenter
        fun showResultView(matchResult: Boolean)
        fun showDialog()
    }

    interface Presenter {
        fun checkSecurityCode(
            securityCode: String,
            email: String,
            phone: String
        )
    }
}