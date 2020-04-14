package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.place.PlaceCallback
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class MyPlacePresenter(
    private val memberRepository: MemberRepository,
    private val placeRepository: PlaceRepository,
    private val view: MyPlaceContract.View
) : MyPlaceContract.Presenter {
    override fun getMyPlaceList(memberNumber: Int) {
        placeRepository.getMyPlaceList(memberNumber, object : PlaceCallback<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                view.showMyPlaceList(response)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
            }

        })
    }

    override fun getUser() {
        memberRepository.getMember(object : MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                view.showUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deletePlace(placeNumber: Int, memberNumber: Int) {
        placeRepository.deletePlace(placeNumber, memberNumber, object : PlaceCallback<Boolean>{
            override fun onSuccess(response: Boolean) {
                view.showDeleteResult(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}