package com.yeonae.chamelezone.view.review.presenter

interface ReviewContract {
    interface View {
        var presenter: Presenter
        fun review(message: String)
    }
    interface Presenter {
        fun reviewCreate(
            memberNumber: Int,
            placeNumber: Int,
            content: String,
            images: String
        )
    }
}