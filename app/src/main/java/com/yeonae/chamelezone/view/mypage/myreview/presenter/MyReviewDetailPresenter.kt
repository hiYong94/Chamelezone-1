package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
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
            object : ReviewCallBack<ReviewResponse> {
                override fun onSuccess(response: ReviewResponse) {
                    myReviewDetailView.showMyReviewDetail(response)
                }

                override fun onFailure(message: String) {

                }
            })
    }
}