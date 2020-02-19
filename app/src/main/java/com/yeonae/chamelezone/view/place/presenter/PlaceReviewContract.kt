package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.network.model.ReviewResponse

interface PlaceReviewContract {
    interface View {
        var presenter: Presenter
        fun placeReview(reviewList: List<ReviewResponse>)
    }

    interface Presenter {
        fun placeDetailReview(placeNumber: Int)
    }
}