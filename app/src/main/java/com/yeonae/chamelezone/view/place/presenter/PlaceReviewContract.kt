package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface PlaceReviewContract {
    interface View {
        var presenter: Presenter
        fun showPlaceReview(reviewList: List<ReviewResponse>)
        fun showReviewDelete(message: String)
        fun showMemberReview(user: UserEntity)
        fun getMemberCheck(response: Boolean)
    }

    interface Presenter {
        fun placeDetailReview(placeNumber: Int)
        fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int)
        fun getMember()
        fun checkMember()
    }
}