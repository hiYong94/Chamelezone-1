package com.yeonae.chamelezone.data.repository.review

interface ReviewRepository {
    fun createReview(
        placeName: String,
        nickname: String,
        reviewImg: String,
        content: String,
        callBack: ReviewCallBack<String>
    )

    fun getReview()
    fun updateReview()
    fun deleteReview()
}