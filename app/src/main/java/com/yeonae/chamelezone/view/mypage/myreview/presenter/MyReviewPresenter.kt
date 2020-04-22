package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.review.ReviewCallback
import com.yeonae.chamelezone.data.repository.review.ReviewRepository
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class MyReviewPresenter(
    private val reviewRepository: ReviewRepository,
    private val memberRepository: MemberRepository,
    private val myReviewView: MyReviewContract.View
) : MyReviewContract.Presenter {
    override fun getUserReview(memberNumber: Int) {
        reviewRepository.getMyReviewList(
            memberNumber,
            object : ReviewCallback<List<ReviewResponse>> {
                override fun onSuccess(response: List<ReviewResponse>) {
                    val reviewItemList = arrayListOf<ReviewItem>()
                    response.forEach {
                        it.toReviewItem().let { reviewItem -> reviewItemList.add(reviewItem) }
                    }
                    myReviewView.showMyReviewList(reviewItemList)
                }

                override fun onFailure(message: String) {

                }
            })
    }

    override fun getMember() {
        memberRepository.getMember(object : MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                myReviewView.getMember(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun checkMember() {
        memberRepository.checkLogin(object : MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                myReviewView.getMemberCheck(response)
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
                    myReviewView.showReviewDelete(response)
                }

                override fun onFailure(message: String) {

                }
            })
    }

    override fun getReview(placeNumber: Int, reviewNumber: Int) {
        reviewRepository.getReviewDetail(
            placeNumber,
            reviewNumber,
            object : ReviewCallback<ReviewResponse> {
                override fun onSuccess(response: ReviewResponse) {
                    response.toReviewItem().let { myReviewView.getReview(it) }
                }

                override fun onFailure(message: String) {

                }
            })
    }
}