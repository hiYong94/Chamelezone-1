package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class PlaceInfoPresenter(
    private val placeRepository: PlaceRepository,
    private val memberRepository: MemberRepository,
    private val view: PlaceInfoContract.View
) : PlaceInfoContract.Presenter {
    override fun placeDetail(placeNumber: Int, memberNumber: Int) {
        placeRepository.getPlaceDetail(placeNumber, memberNumber, object : PlaceCallBack<PlaceResponse>{
            override fun onSuccess(response: PlaceResponse) {
                view.placeInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getUser() {
        memberRepository.getMember(object : MemberCallBack<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                view.showUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}