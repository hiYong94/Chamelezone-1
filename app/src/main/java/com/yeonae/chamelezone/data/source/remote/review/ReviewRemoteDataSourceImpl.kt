package com.yeonae.chamelezone.data.source.remote.review

import com.yeonae.chamelezone.data.repository.review.ReviewCallBack

class ReviewRemoteDataSourceImpl : ReviewRemoteDataSource {
    override fun createReview(
        placeName: String,
        nickname: String,
        reviewImg: String,
        content: String,
        callBack: ReviewCallBack<String>
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}