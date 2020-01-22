package com.yeonae.chamelezone.view.mypage.myplace.presenter

interface PlaceContract {

    interface View {
        var presenter: Presenter
        fun place(message: String)
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
    }
}