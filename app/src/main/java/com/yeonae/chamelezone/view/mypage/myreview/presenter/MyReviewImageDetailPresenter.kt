package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.data.repository.review.ReviewRepository
import com.yeonae.chamelezone.network.model.ReviewResponse

class MyReviewImageDetailPresenter(
    private val reviewRepository: ReviewRepository,
    private val reviewDetailView: MyReviewImageDetailContract.View
) : MyReviewImageDetailContract.Presenter{
    override fun getReview(placeNumber: Int, reviewNumber: Int) {
        reviewRepository.getReviewDetail(placeNumber, reviewNumber, object :
            ReviewCallBack<ReviewResponse> {
            override fun onSuccess(response: ReviewResponse) {
                reviewDetailView.showReviewImage(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}