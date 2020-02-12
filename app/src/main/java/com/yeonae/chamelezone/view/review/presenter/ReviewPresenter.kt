package com.yeonae.chamelezone.view.review.presenter

import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.data.repository.review.ReviewRepository

class ReviewPresenter(
    private val reviewRepository: ReviewRepository,
    private val reviewView: ReviewContract.View
) : ReviewContract.Presenter {
    override fun reviewCreate(
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        images: String
    ) {
        reviewRepository.createReview(
            memberNumber,
            placeNumber,
            content,
            images,
            object : ReviewCallBack<String> {
                override fun onSuccess(response: String) {
                    reviewView.review(response)
                }

                override fun onFailure(message: String) {

                }
            })
    }
}