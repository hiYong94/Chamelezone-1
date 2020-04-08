package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import java.math.BigDecimal

interface PlaceModifyContract {

    interface View {
        var presenter: Presenter
        fun showPlaceDetail(place: PlaceResponse)
        fun showKeywordList(response: List<KeywordResponse>)
        fun showResult(response: Boolean)
    }

    interface Presenter {
        fun getPlaceDetail(placeNumber: Int, memberNumber: Int?)
        fun getKeyword()
        fun updatePlace(
            placeNumber: Int,
            images: List<String>,
            memberNumber: Int,
            address: String,
            phoneNumber: String,
            content: String,
            latitude: BigDecimal,
            longitude: BigDecimal,
            imageNumber: List<Int>
        )
    }
}