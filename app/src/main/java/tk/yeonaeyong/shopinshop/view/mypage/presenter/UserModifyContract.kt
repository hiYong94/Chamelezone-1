package tk.yeonaeyong.shopinshop.view.mypage.presenter

import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface UserModifyContract {

    interface View {
        var presenter: Presenter
        fun showUserInfo(user: UserEntity)
        fun showMessage(response: Boolean)
        fun showNicknameMessage(nicknameCheck: String)
    }

    interface Presenter {
        fun getUser()

        fun updateMember(
            memberNumber: Int,
            password: String?,
            nickName: String,
            phone: String
        )

        fun checkNickname(nickName: String)
    }
}