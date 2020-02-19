package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.data.repository.like.LikeCallBack
import com.yeonae.chamelezone.data.repository.like.LikeRepository
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class PlaceDetailPresenter(
    private val memberRepository: MemberRepository,
    private val placeRepository: PlaceRepository,
    private val likeRepository: LikeRepository,
    private val view: PlaceDetailContract.View
) :PlaceDetailContract.Presenter {
    override fun placeDetail(placeNumber: Int, memberNumber: Int) {
        placeRepository.getPlaceDetail(placeNumber, memberNumber, object : PlaceCallBack<PlaceResponse>{
            override fun onSuccess(response: PlaceResponse) {
                view.placeInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

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
                view.showUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun selectLike(memberNumber: Int, placeNumber: Int) {
        likeRepository.selectLike(memberNumber, placeNumber, object : LikeCallBack<String>{
            override fun onSuccess(response: String) {
                view.showLikeState(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deleteLike(likeNumber: Int, memberNumber: Int, placeNumber: Int) {
        likeRepository.deleteLike(likeNumber, memberNumber, placeNumber, object : LikeCallBack<String>{
            override fun onSuccess(response: String) {
                view.showLikeState(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}