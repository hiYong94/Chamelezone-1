package com.yeonae.chamelezone.data.source.remote.review

import com.yeonae.chamelezone.data.repository.review.ReviewCallback
import com.yeonae.chamelezone.network.model.ReviewResponse

interface ReviewRemoteDataSource {
    fun createReview(
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        images: List<String>,
        callback: ReviewCallback<String>
    )

    fun updateReview(
        images: List<String>,
        reviewNumber: Int,
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        deleteImageNumber: List<Int>,
        callback: ReviewCallback<Boolean>
    )

    fun getReviewList(placeNumber: Int, callback: ReviewCallback<List<ReviewResponse>>)
    fun getMyReviewList(memberNumber: Int, callback: ReviewCallback<List<ReviewResponse>>)
    fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int, callback: ReviewCallback<String>)
    fun getReviewDetail(placeNumber: Int, reviewNumber: Int, callback: ReviewCallback<ReviewResponse>)
    fun getMyReviewDetail(placeNumber: Int, reviewNumber: Int, callback: ReviewCallback<ReviewResponse>)
    fun getMyReviewImageDetail(placeNumber: Int, reviewNumber: Int, callback: ReviewCallback<ReviewResponse>)
}