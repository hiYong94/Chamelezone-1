package com.yeonae.chamelezone.data.repository.review

import com.yeonae.chamelezone.network.model.ReviewResponse

interface ReviewRepository {
    fun createReview(
        placeName: String,
        nickname: String,
        reviewImg: String,
        content: String,
        callBack: ReviewCallBack<String>
    )

    fun getReview()
    fun getMyReviewList(userId: String, callBack: ReviewCallBack<List<ReviewResponse>>)
    fun updateReview()
    fun deleteReview()
}