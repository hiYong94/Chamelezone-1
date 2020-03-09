package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class MyPlacePresenter(
    private val memberRepository: MemberRepository,
    private val placeRepository: PlaceRepository,
    private val view: MyPlaceContract.View
) : MyPlaceContract.Presenter {
    override fun getMyPlaceList(memberNumber: Int) {
        placeRepository.getMyPlaceList(memberNumber, object : PlaceCallBack<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                view.showMyPlaceList(response)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
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