package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.network.model.ReviewResponse

interface MyReviewImageDetailContract {
    interface View {
        var presenter: Presenter
        fun showReviewImage(review: ReviewResponse)

    }

    interface Presenter {
        fun getReview(placeNumber: Int, reviewNumber: Int)
    }
}