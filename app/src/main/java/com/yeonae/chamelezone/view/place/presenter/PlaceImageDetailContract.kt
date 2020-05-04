package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse

interface PlaceImageDetailContract {
    interface View {
        var presenter: Presenter
        fun showPlaceImage(place: PlaceResponse)

    }

    interface Presenter {
        fun getPlace(placeNumber: Int, memberNumber: Int?)
    }
}