package com.yeonae.chamelezone.data.repository.review

import com.yeonae.chamelezone.network.model.ReviewResponse

interface ReviewRepository {
    fun createReview(
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        images: List<String>,
        callBack: ReviewCallBack<String>
    )

    fun getReviewList(placeNumber: Int, callBack: ReviewCallBack<List<ReviewResponse>>)
    fun getMyReviewList(memberNumber: Int, callBack: ReviewCallBack<List<ReviewResponse>>)
    fun updateReview()
    fun deleteReview()
}