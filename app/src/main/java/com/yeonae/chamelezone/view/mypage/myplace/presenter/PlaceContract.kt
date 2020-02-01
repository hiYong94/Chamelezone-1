package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.network.model.KeywordResponse

interface PlaceContract {

    interface View {
        var presenter: Presenter
        fun place(message: String)
        fun showKeywordList(response: List<KeywordResponse>)
    }

    interface Presenter {
        fun placeRegister(
            keywordName: String,
            name: String,
            address: String,
            openingTime: String,
            phoneNumber: String,
            content: String,
            latitude: String,
            longitude: String)

        fun getKeyword()
    }
}