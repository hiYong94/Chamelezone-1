package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.network.model.ReviewResponse

interface MyReviewDetailContract {
    interface View {
        var presenter: Presenter
        fun showMyReviewDetail(review: ReviewResponse)
    }

    interface Presenter {
        fun getMyReviewDetail(placeNumber: Int, reviewNumber: Int)
    }
}