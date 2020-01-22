package com.yeonae.chamelezone.view.search.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse

interface SearchContract {

    interface View {
        var presenter: Presenter
        fun showPlaceList(placeList: List<PlaceResponse>)
    }

    interface Presenter {
        fun searchByName(placeName: String)
        fun searchByAddress(address:String)
        fun searchByKeyword(keyword: String)
    }
}