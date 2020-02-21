package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import java.math.BigDecimal

interface PlaceContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
        fun showKeywordList(response: List<KeywordResponse>)
        fun showUserInfo(user: UserEntity)
    }

    interface Presenter {
        fun placeRegister(
            memberNumber: Int,
            keywordName: List<Int>,
            name: String,
            address: String,
            openingTime: List<String>,
            phoneNumber: String,
            content: String,
            latitude: BigDecimal,
            longitude: BigDecimal,
            images: List<String>
        )

        fun getKeyword()
        fun getUser()
    }
}