package com.yeonae.chamelezone.view.home.presenter

import com.yeonae.chamelezone.data.repository.like.LikeCallback
import com.yeonae.chamelezone.data.repository.like.LikeRepository
import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.place.PlaceCallback
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.LikeResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class HomePresenter(
    private val repository: PlaceRepository,
    private val memberRepository: MemberRepository,
    private val likeRepository: LikeRepository,
    private val view: HomeContract.View
) : HomeContract.Presenter {
    override fun getHomeList(memberNumber: Int?) {
        repository.getHomePlaceList(memberNumber, object : PlaceCallback<List<PlaceResponse>> {
            override fun onSuccess(placeList: List<PlaceResponse>) {
                view.showHomeList(placeList)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun getMember() {
        memberRepository.getMember(object : MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                view.getMember(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun checkMember() {
        memberRepository.checkLogin(object : MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.getMemberCheck(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun selectLike(memberNumber: Int, placeNumber: Int) {
        likeRepository.selectLike(memberNumber, placeNumber, object : LikeCallback<LikeResponse> {
            override fun onSuccess(response: LikeResponse) {
                view.showLikeMessage(response.toLikeStatusItem())
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deleteLike(memberNumber: Int, placeNumber: Int) {
        likeRepository.deleteLike(memberNumber, placeNumber, object : LikeCallback<LikeResponse> {
            override fun onSuccess(response: LikeResponse) {
                view.showDeleteLikeMessage(response.toLikeStatusItem())
            }

            override fun onFailure(message: String) {

            }

        })
    }
}