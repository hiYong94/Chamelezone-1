package com.yeonae.chamelezone.data.repository.review

import com.yeonae.chamelezone.data.source.remote.review.ReviewRemoteDataSource
import com.yeonae.chamelezone.network.model.ReviewResponse

class ReviewRepositoryImpl private constructor(private val reviewRemoteDataSource: ReviewRemoteDataSource) :
    ReviewRepository {
    override fun createReview(
        placeName: String,
        nickname: String,
        reviewImg: String,
        content: String,
        callBack: ReviewCallBack<String>
    ) {
        reviewRemoteDataSource.createReview(placeName, nickname, reviewImg, content, callBack)
    }

    override fun getReview() {

    }

    override fun getMyReviewList(userId: String, callBack: ReviewCallBack<List<ReviewResponse>>) {
        reviewRemoteDataSource.getMyReviewList(userId, callBack)
    }

    override fun updateReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun getInstance(remoteDataSource: ReviewRemoteDataSource): ReviewRepository =
            ReviewRepositoryImpl(remoteDataSource)
    }
}