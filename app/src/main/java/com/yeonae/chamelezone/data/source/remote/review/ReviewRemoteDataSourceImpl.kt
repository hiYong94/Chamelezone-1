package com.yeonae.chamelezone.data.source.remote.review

import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.network.model.ReviewResponse

class ReviewRemoteDataSourceImpl : ReviewRemoteDataSource {
    override fun createReview(
        placeName: String,
        nickname: String,
        reviewImg: String,
        content: String,
        result: String,
        callBack: ReviewCallBack<String>
    ) {

    }

    override fun getReview() {

    }

    override fun getMyReviewList(userId: String, callBack: ReviewCallBack<List<ReviewResponse>>) {

    }

    override fun updateReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}