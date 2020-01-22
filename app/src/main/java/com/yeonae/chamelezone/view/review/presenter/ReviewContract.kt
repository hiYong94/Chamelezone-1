package com.yeonae.chamelezone.view.review.presenter

interface ReviewContract {
    interface View {
        var presenter: Presenter
        fun Review(message: String)
    }
    interface Presenter {
        fun reviewCreate(
            placeName: String,
            nickname: String,
            reviewImg: String,
            content: String
        )
    }
}