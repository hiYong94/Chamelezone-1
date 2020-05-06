package tk.yeonaeyong.shopinshop.view.mypage.presenter

import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface MypageContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
        fun showResultView(response: Boolean)
        fun showUserInfo(user: UserEntity)
    }

    interface Presenter {
        fun logout()
        fun checkLogin()
        fun getUser()
        fun deleteUser(memberNumber: Int)
    }
}