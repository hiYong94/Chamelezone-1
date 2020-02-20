package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.data.repository.review.ReviewRepository
import com.yeonae.chamelezone.network.model.ReviewResponse

class PlaceReviewPresenter(private val reviewRepository: ReviewRepository,
                           private val placeReviewView: PlaceReviewContract.View
) : PlaceReviewContract.Presenter {
    override fun placeDetailReview(placeNumber: Int) {
        reviewRepository.getReviewList(placeNumber, object : ReviewCallBack<List<ReviewResponse>> {
            override fun onSuccess(response: List<ReviewResponse>) {
                placeReviewView.showPlaceReview(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}