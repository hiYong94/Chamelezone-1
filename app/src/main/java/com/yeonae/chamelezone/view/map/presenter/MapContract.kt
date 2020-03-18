package com.yeonae.chamelezone.view.map.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse

interface MapContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(placeList: List<PlaceResponse>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun searchPlace(placeName: String)
    }
}