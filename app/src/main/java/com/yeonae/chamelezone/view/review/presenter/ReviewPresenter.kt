package com.yeonae.chamelezone.view.review.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.data.repository.review.ReviewRepository
import com.yeonae.chamelezone.network.room.entity.UserEntity

class ReviewPresenter(
    private val reviewRepository: ReviewRepository,
    private val memberRepository: MemberRepository,
    private val reviewView: ReviewContract.View
) : ReviewContract.Presenter {
    override fun reviewCreate(
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        images: List<String>
    ) {
        reviewRepository.createReview(
            memberNumber,
            placeNumber,
            content,
            images,
            object : ReviewCallBack<String> {
                override fun onSuccess(response: String) {
                    reviewView.review(response)
                }

                override fun onFailure(message: String) {

                }
            })
    }

    override fun getMember() {
        memberRepository.getMember(object : MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                reviewView.getMember(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun checkMember() {
        memberRepository.checkLogin(object : MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                reviewView.getMemberCheck(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}