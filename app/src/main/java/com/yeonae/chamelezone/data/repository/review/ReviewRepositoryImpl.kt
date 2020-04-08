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

    override fun updateReview(
        images: List<String>,
        reviewNumber: Int,
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        imageNumber: List<Int>,
        callBack: ReviewCallBack<Boolean>
    ) {
        reviewRemoteDataSource.updateReview(
            images,
            reviewNumber,
            memberNumber,
            placeNumber,
            content,
            imageNumber,
            callBack
        )
    }

    override fun getReviewList(placeNumber: Int, callBack: ReviewCallBack<List<ReviewResponse>>) {
        reviewRemoteDataSource.getReviewList(placeNumber, callBack)
    }

    override fun getMyReviewList(memberNumber: Int, callBack: ReviewCallBack<List<ReviewResponse>>) {
        reviewRemoteDataSource.getMyReviewList(memberNumber, callBack)
    }

    override fun deleteReview(
        placeNumber: Int,
        reviewNumber: Int,
        memberNumber: Int,
        callBack: ReviewCallBack<String>
    ) {
        reviewRemoteDataSource.deleteReview(placeNumber, reviewNumber, memberNumber, callBack)
    }

    override fun getReviewDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callBack: ReviewCallBack<ReviewResponse>
    ) {
        reviewRemoteDataSource.getReviewDetail(placeNumber, reviewNumber, callBack)
    }

    override fun getMyReviewDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callBack: ReviewCallBack<ReviewResponse>
    ) {
        reviewRemoteDataSource.getReviewDetail(placeNumber, reviewNumber, callBack)
    }

    override fun getMyReviewImageDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callBack: ReviewCallBack<ReviewResponse>
    ) {
        reviewRemoteDataSource.getReviewDetail(placeNumber, reviewNumber, callBack)
    }

    companion object {
        fun getInstance(remoteDataSource: ReviewRemoteDataSource): ReviewRepository =
            ReviewRepositoryImpl(remoteDataSource)
    }
}