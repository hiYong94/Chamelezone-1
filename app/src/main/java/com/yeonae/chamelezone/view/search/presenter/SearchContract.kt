package com.yeonae.chamelezone.view.search.presenter

import com.yeonae.chamelezone.data.model.PlaceItem

interface SearchContract {

    interface View {
        var presenter: Presenter
        fun showPlaceList(placeList: List<PlaceItem>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun searchByName(placeName: String)
        fun searchByAddress(address: String)
        fun searchByKeyword(keyword: String)
    }
}