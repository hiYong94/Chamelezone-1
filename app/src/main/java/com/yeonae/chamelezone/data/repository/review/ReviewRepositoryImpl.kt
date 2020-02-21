package com.yeonae.chamelezone.data.repository.review

import com.yeonae.chamelezone.data.source.remote.review.ReviewRemoteDataSource
import com.yeonae.chamelezone.network.model.ReviewResponse

class ReviewRepositoryImpl private constructor(private val reviewRemoteDataSource: ReviewRemoteDataSource) :
    ReviewRepository {
    override fun createReview(
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        images: List<String>,
        callBack: ReviewCallBack<String>
    ) {
        reviewRemoteDataSource.createReview(memberNumber, placeNumber, content, images, callBack)
    }

    override fun getReviewList(placeNumber: Int, callBack: ReviewCallBack<List<ReviewResponse>>) {
        reviewRemoteDataSource.getReviewList(placeNumber, callBack)
    }

    override fun getMyReviewList(memberNumber: Int, callBack: ReviewCallBack<List<ReviewResponse>>) {
        reviewRemoteDataSource.getMyReviewList(memberNumber, callBack)
    }

    override fun updateReview() {

    }

    override fun deleteReview() {

    }

    companion object {
        fun getInstance(remoteDataSource: ReviewRemoteDataSource): ReviewRepository =
            ReviewRepositoryImpl(remoteDataSource)
    }
}