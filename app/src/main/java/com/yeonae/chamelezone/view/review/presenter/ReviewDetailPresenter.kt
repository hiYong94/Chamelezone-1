package com.yeonae.chamelezone.view.review.presenter

import com.yeonae.chamelezone.data.repository.review.ReviewCallback
import com.yeonae.chamelezone.data.repository.review.ReviewRepository
import com.yeonae.chamelezone.network.model.ReviewResponse

class ReviewDetailPresenter(
    private val reviewRepository: ReviewRepository,
    private val reviewDetailView: ReviewDetailContract.View
) : ReviewDetailContract.Presenter {
    override fun getReview(placeNumber: Int, reviewNumber: Int) {
        reviewRepository.getReviewDetail(placeNumber, reviewNumber, object : ReviewCallback<ReviewResponse> {
            override fun onSuccess(response: ReviewResponse) {
                reviewDetailView.showReviewImage(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}