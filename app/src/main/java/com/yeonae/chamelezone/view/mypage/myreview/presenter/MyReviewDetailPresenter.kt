package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.data.repository.review.ReviewCallback
import com.yeonae.chamelezone.data.repository.review.ReviewRepository
import com.yeonae.chamelezone.network.model.ReviewResponse

class MyReviewDetailPresenter(
    private val reviewRepository: ReviewRepository,
    private val myReviewDetailView: MyReviewDetailContract.View
) : MyReviewDetailContract.Presenter {
    override fun getMyReviewDetail(placeNumber: Int, reviewNumber: Int) {
        reviewRepository.getMyReviewDetail(
            placeNumber,
            reviewNumber,
            object : ReviewCallback<ReviewResponse> {
                override fun onSuccess(response: ReviewResponse) {
                    response.toReviewItem()?.let { myReviewDetailView.showMyReviewDetail(it) }
                }

                override fun onFailure(message: String) {

                }
            })
    }
}