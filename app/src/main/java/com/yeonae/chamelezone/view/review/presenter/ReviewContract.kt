package com.yeonae.chamelezone.view.review.presenter

import com.yeonae.chamelezone.network.room.entity.UserEntity

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