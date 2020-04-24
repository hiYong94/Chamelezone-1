package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.review.ReviewCallback
import com.yeonae.chamelezone.data.repository.review.ReviewRepository
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class PlaceReviewPresenter(
    private val reviewRepository: ReviewRepository,
    private val memberRepository: MemberRepository,
    private val placeReviewView: PlaceReviewContract.View
) : PlaceReviewContract.Presenter {
    override fun placeDetailReview(placeNumber: Int) {
        reviewRepository.getReviewList(placeNumber, object : ReviewCallback<List<ReviewResponse>> {
            override fun onSuccess(response: List<ReviewResponse>) {
                val reviewItemList = arrayListOf<ReviewItem>()
                response.forEach {
                    it.toReviewItem().let { reviewItem -> reviewItemList.add(reviewItem) }
                }
                placeReviewView.showPlaceReview(reviewItemList)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int) {
        reviewRepository.deleteReview(
            placeNumber,
            reviewNumber,
            memberNumber,
            object : ReviewCallback<String> {
                override fun onSuccess(response: String) {
                    placeReviewView.showReviewDelete(response)
                }

                override fun onFailure(message: String) {

                }
            })
    }

    override fun getMember() {
        memberRepository.getMember(object : MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                placeReviewView.showMemberReview(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun checkMember() {
        memberRepository.checkLogin(object : MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                placeReviewView.getMemberCheck(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}