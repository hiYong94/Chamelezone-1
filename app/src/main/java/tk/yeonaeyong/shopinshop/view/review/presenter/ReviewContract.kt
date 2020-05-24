package tk.yeonaeyong.shopinshop.view.review.presenter

import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface ReviewContract {
    interface View {
        var presenter: Presenter
        fun review(message: String)
        fun getMember(user: UserEntity)
        fun getMemberCheck(response: Boolean)
    }

    interface Presenter {
        fun reviewCreate(
            memberNumber: Int,
            placeNumber: Int,
            content: String,
            images: List<String>
        )

        fun getMember()
        fun checkMember()
    }
}