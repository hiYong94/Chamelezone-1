package com.yeonae.chamelezone.view.map.presenter

import com.yeonae.chamelezone.data.model.MapItem

interface MapContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(placeList: List<MapItem>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun searchPlace(placeName: String)
    }
}