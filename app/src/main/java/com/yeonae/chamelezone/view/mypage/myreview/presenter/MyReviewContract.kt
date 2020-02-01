package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.network.model.ReviewResponse

interface MyReviewContract {
    interface View {
        var presenter: Presenter
        fun showMyReviewList(reviewList: List<ReviewResponse>)
    }

    interface Presenter {
        fun userReview(userId: String)
    }
}