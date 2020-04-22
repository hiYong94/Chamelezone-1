package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface MyReviewContract {
    interface View {
        var presenter: Presenter
        fun showMyReviewList(reviewList: List<ReviewItem>)
        fun getMember(user: UserEntity)
        fun getMemberCheck(response: Boolean)
        fun showReviewDelete(message: String)
        fun getReview(review: ReviewItem)
    }

    interface Presenter {
        fun getUserReview(memberNumber: Int)
        fun getMember()
        fun checkMember()
        fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int)
        fun getReview(placeNumber: Int, reviewNumber: Int)
    }
}