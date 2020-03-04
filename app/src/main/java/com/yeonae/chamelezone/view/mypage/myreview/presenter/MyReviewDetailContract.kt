package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.data.model.ReviewItem

interface MyReviewDetailContract {
    interface View {
        var presenter: Presenter
        fun showMyReviewDetail(review: ReviewItem)
    }

    interface Presenter {
        fun getMyReviewDetail(placeNumber: Int, reviewNumber: Int)
    }
}