package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse

interface PlaceReviewContract {
    interface View {
        var presenter: Presenter
        fun placeReview(place: PlaceResponse)
    }

    interface Presenter {
        fun placeDetailReview(placeNumber: Int)
    }
}