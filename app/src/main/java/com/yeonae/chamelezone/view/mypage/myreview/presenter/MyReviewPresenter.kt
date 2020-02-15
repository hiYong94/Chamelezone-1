package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.data.repository.review.ReviewRepository
import com.yeonae.chamelezone.network.model.ReviewResponse

class MyReviewPresenter(
    private val reviewRepository: ReviewRepository,
    private val myReviewView: MyReviewContract.View
) : MyReviewContract.Presenter {
    override fun userReview(memberNumber: Int) {
        reviewRepository.getMyReviewList(memberNumber, object : ReviewCallBack<List<ReviewResponse>> {
            override fun onSuccess(response: List<ReviewResponse>) {
                myReviewView.showMyReviewList(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}