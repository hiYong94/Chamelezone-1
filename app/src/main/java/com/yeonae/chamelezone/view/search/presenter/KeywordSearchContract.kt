package com.yeonae.chamelezone.view.search.presenter

import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.network.model.KeywordResponse

interface KeywordSearchContract {

    interface View {
        var presenter: Presenter
        fun showKeywordList(response: List<KeywordResponse>)
        fun showPlaceList(placeList: List<PlaceItem>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun getKeyword()
        fun searchByKeyword(keyword: String)
    }
}