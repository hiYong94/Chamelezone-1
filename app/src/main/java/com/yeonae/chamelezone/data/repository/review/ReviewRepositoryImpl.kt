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
        callback: ReviewCallback<String>
    ) {
        reviewRemoteDataSource.createReview(memberNumber, placeNumber, content, images, callback)
    }

    override fun updateReview(
        images: List<String>,
        reviewNumber: Int,
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        deleteImageNumber: List<Int>,
        callback: ReviewCallback<Boolean>
    ) {
        reviewRemoteDataSource.updateReview(
            images,
            reviewNumber,
            memberNumber,
            placeNumber,
            content,
            deleteImageNumber,
            callback
        )
    }

    override fun getReviewList(placeNumber: Int, callback: ReviewCallback<List<ReviewResponse>>) {
        reviewRemoteDataSource.getReviewList(placeNumber, callback)
    }

    override fun getMyReviewList(memberNumber: Int, callback: ReviewCallback<List<ReviewResponse>>) {
        reviewRemoteDataSource.getMyReviewList(memberNumber, callback)
    }

    override fun deleteReview(
        placeNumber: Int,
        reviewNumber: Int,
        memberNumber: Int,
        callback: ReviewCallback<String>
    ) {
        reviewRemoteDataSource.deleteReview(placeNumber, reviewNumber, memberNumber, callback)
    }

    override fun getReviewDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callback: ReviewCallback<ReviewResponse>
    ) {
        reviewRemoteDataSource.getReviewDetail(placeNumber, reviewNumber, callback)
    }

    override fun getMyReviewDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callback: ReviewCallback<ReviewResponse>
    ) {
        reviewRemoteDataSource.getReviewDetail(placeNumber, reviewNumber, callback)
    }

    override fun getMyReviewImageDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callback: ReviewCallback<ReviewResponse>
    ) {
        reviewRemoteDataSource.getReviewDetail(placeNumber, reviewNumber, callback)
    }

    companion object {
        fun getInstance(remoteDataSource: ReviewRemoteDataSource): ReviewRepository =
            ReviewRepositoryImpl(remoteDataSource)
    }
}