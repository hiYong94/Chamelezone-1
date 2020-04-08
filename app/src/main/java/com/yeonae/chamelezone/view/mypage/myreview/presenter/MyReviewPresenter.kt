package com.yeonae.chamelezone.view.mypage.myreview.presenter

import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.data.repository.review.ReviewRepository
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class MyReviewPresenter(
    private val reviewRepository: ReviewRepository,
    private val memberRepository: MemberRepository,
    private val myReviewView: MyReviewContract.View
) : MyReviewContract.Presenter {
    override fun getUserReview(memberNumber: Int) {
        reviewRepository.getMyReviewList(memberNumber, object : ReviewCallBack<List<ReviewResponse>> {
            override fun onSuccess(response: List<ReviewResponse>) {
                val reviewItemList = arrayListOf<ReviewItem>()
                response.forEach {
                    it.toReviewItem().let { it1 -> reviewItemList.add(it1) }
                }
                myReviewView.showMyReviewList(reviewItemList)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun getMember() {
        memberRepository.getMember(object : MemberCallBack<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                myReviewView.getMember(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun checkMember() {
        memberRepository.checkLogin(object : MemberCallBack<Boolean> {
            override fun onSuccess(response: Boolean) {
                myReviewView.getMemberCheck(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int) {
        reviewRepository.deleteReview(placeNumber, reviewNumber, memberNumber, object : ReviewCallBack<String> {
            override fun onSuccess(response: String) {
                myReviewView.showReviewDelete(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}