package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse

interface PlaceInfoContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(place: PlaceResponse)
    }

    interface Presenter {
        fun placeDetail(placeNumber: Int)
    }
}