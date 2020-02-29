package com.yeonae.chamelezone.view.review.presenter

import com.yeonae.chamelezone.network.model.ReviewResponse

interface ReviewDetailContract {
    interface View {
        var presenter: Presenter
        fun showReviewImage(review: ReviewResponse)

    }

    interface Presenter {
        fun getReview(placeNumber: Int, reviewNumber: Int)
    }
}