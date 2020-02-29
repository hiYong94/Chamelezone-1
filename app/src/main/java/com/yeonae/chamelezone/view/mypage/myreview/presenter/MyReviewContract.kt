package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface MyReviewContract {
    interface View {
        var presenter: Presenter
        fun showMyReviewList(reviewList: List<ReviewResponse>)
        fun getMember(user: UserEntity)
        fun getMemberCheck(response: Boolean)
    }

    interface Presenter {
        fun getUserReview(memberNumber: Int)
        fun getMember()
        fun checkMember()
    }
}