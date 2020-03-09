package com.yeonae.chamelezone.view.like.presenter

import com.yeonae.chamelezone.data.model.LikeStatusItem
import com.yeonae.chamelezone.data.repository.like.LikeCallBack
import com.yeonae.chamelezone.data.repository.like.LikeRepository
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.model.LikeResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class LikePresenter(
    private val memberRepository: MemberRepository,
    private val likeRepository: LikeRepository,
    private val view: LikeContract.View
) : LikeContract.Presenter {
    override fun checkLogin() {
        memberRepository.checkLogin(object : MemberCallBack<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showResultView(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getUser() {
        memberRepository.getMember(object : MemberCallBack<UserEntity>{
            override fun onSuccess(response: UserEntity) {
                response.userNumber?.let { getMyLikeList(it) }
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deleteLike(memberNumber: Int, placeNumber: Int) {
        likeRepository.deleteLike(memberNumber, placeNumber, object :
            LikeCallBack<LikeResponse> {
            override fun onSuccess(response: LikeResponse) {
                view.showLikeState(response.toLikeStatusItem(response))
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getMyLikeList(memberNumber: Int) {
        likeRepository.getMyLikeList(memberNumber, object : LikeCallBack<List<PlaceResponse>>{
            override fun onSuccess(response: List<PlaceResponse>) {
                view.showMyLikeList(response)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
            }

        })
    }
}