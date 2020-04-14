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

    fun updateReview(
        images: List<String>,
        reviewNumber: Int,
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        imageNumber: List<Int>,
        callBack: ReviewCallBack<Boolean>
    )

    fun getReviewList(placeNumber: Int, callBack: ReviewCallBack<List<ReviewResponse>>)
    fun getMyReviewList(memberNumber: Int, callBack: ReviewCallBack<List<ReviewResponse>>)
    fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int, callBack: ReviewCallBack<String>)
    fun getReviewDetail(placeNumber: Int, reviewNumber: Int, callBack: ReviewCallBack<ReviewResponse>)
    fun getMyReviewDetail(placeNumber: Int, reviewNumber: Int, callBack: ReviewCallBack<ReviewResponse>)
    fun getMyReviewImageDetail(placeNumber: Int, reviewNumber: Int, callBack: ReviewCallBack<ReviewResponse>)
}