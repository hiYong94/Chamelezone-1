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
    fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int, callBack: ReviewCallBack<String>)
    fun getReviewDetail(placeNumber: Int, reviewNumber: Int, callBack: ReviewCallBack<ReviewResponse>)
    fun getMyReviewDetail(placeNumber: Int, reviewNumber: Int, callBack: ReviewCallBack<ReviewResponse>)
    fun getMyReviewImageDetail(placeNumber: Int, reviewNumber: Int, callBack: ReviewCallBack<ReviewResponse>)
}