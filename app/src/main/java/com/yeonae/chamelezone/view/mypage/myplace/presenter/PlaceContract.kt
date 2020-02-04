package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.network.model.KeywordResponse
import java.math.BigDecimal

interface PlaceContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
        fun showKeywordList(response: List<KeywordResponse>)
    }

    interface Presenter {
        fun placeRegister(
            keywordName: MutableList<Int>,
            name: String,
            address: String,
            openingTime: MutableList<String>,
            phoneNumber: String,
            content: String,
            latitude: BigDecimal,
            longitude: BigDecimal)

        fun getKeyword()
    }
}