package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.data.model.ReviewItem

interface MyReviewImageDetailContract {
    interface View {
        var presenter: Presenter
        fun showReviewImage(review: ReviewItem)

    }

    interface Presenter {
        fun getReview(placeNumber: Int, reviewNumber: Int)
    }
}