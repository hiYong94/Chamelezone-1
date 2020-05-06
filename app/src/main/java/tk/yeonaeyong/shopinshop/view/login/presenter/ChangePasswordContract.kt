package tk.yeonaeyong.shopinshop.view.login.presenter

interface ChangePasswordContract {

    interface View {
        var presenter: Presenter
        fun showResultView(response: Boolean)
    }

    interface Presenter {
        fun changePassword(
            password: String,
            memberNumber: Int
        )
    }
}