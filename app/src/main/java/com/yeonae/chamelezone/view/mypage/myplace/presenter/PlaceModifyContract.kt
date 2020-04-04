package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceResponse

interface PlaceModifyContract {

    interface View {
        var presenter: Presenter
        fun showPlaceDetail(place: PlaceResponse)
        fun showKeywordList(response: List<KeywordResponse>)
    }

    interface Presenter {
        fun getPlaceDetail(placeNumber: Int, memberNumber: Int?)
        fun getKeyword()
    }
}