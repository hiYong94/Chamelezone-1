package com.yeonae.chamelezone.view.review.presenter

class ReviewPresenter(private val reviewView: ReviewContract.View): ReviewContract.Presenter{
    override fun reviewCreate(
        placeName: String,
        nickname: String,
        reviewImg: String,
        content: String,
        result: String
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}