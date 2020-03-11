package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.data.repository.review.ReviewRepository
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class PlaceReviewPresenter(private val reviewRepository: ReviewRepository,
                           private val memberRepository: MemberRepository,
                           private val placeReviewView: PlaceReviewContract.View
) : PlaceReviewContract.Presenter {
    override fun placeDetailReview(placeNumber: Int) {
        reviewRepository.getReviewList(placeNumber, object : ReviewCallBack<List<ReviewResponse>> {
            override fun onSuccess(response: List<ReviewResponse>) {
                val reviewItem = arrayListOf<ReviewItem>()
                response.forEachIndexed { index, _ ->
                    reviewItem.add(response[index].toReviewItem(response[index]))
                }
                placeReviewView.showPlaceReview(reviewItem)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int) {
        reviewRepository.deleteReview(placeNumber, reviewNumber, memberNumber, object : ReviewCallBack<String> {
            override fun onSuccess(response: String) {
                placeReviewView.showReviewDelete(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun getMember() {
        memberRepository.getMember(object : MemberCallBack<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                placeReviewView.showMemberReview(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun checkMember() {
        memberRepository.checkLogin(object : MemberCallBack<Boolean> {
            override fun onSuccess(response: Boolean) {
                placeReviewView.getMemberCheck(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}