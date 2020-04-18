package com.yeonae.chamelezone.view.map.presenter

import com.yeonae.chamelezone.data.model.MapItem

interface MarkerInfoContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(placeList: List<MapItem>)
    }

    interface Presenter {
        fun searchPlace(placeName: String)
    }
}