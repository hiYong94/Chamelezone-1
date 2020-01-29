package com.yeonae.chamelezone.data.source.remote.review

import com.yeonae.chamelezone.data.repository.review.ReviewCallBack

interface ReviewRemoteDataSource {
    fun createReview(
        placeName: String,
        nickname: String,
        reviewImg: String,
        content: String,
        result: String,
        callBack: ReviewCallBack<String>
    )

    fun getReview()
    fun updateReview()
    fun deleteReview()
}