package com.yeonae.chamelezone.view.map.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse

interface MarkerInfoContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(placeList: List<PlaceResponse>)
    }

    interface Presenter {
        fun searchPlace(placeName: String)
    }
}