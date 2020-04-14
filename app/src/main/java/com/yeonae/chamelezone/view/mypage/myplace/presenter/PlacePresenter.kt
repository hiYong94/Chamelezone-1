package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.place.PlaceCallback
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceDuplicateResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import java.math.BigDecimal

class PlacePresenter(
    private val memberRepository: MemberRepository,
    private val placeRepository: PlaceRepository,
    private val view: PlaceContract.View
) : PlaceContract.Presenter {
    override fun placeRegister(
        memberNumber: Int,
        keywordName: List<Int>,
        name: String,
        address: String,
        openingTime: List<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        images: List<String>
    ) {
        placeRepository.registerPlace(
            memberNumber,
            keywordName,
            name,
            address,
            openingTime,
            phoneNumber,
            content,
            latitude,
            longitude,
            images,
            object : PlaceCallback<String> {
                override fun onSuccess(response: String) {
                    view.showMessage(response)
                }

                override fun onFailure(message: String) {

                }

            }
        )
    }

    override fun getKeyword() {
        placeRepository.getKeyword(object : PlaceCallback<List<KeywordResponse>> {
            override fun onSuccess(response: List<KeywordResponse>) {
                view.showKeywordList(response)
            }

            override fun onFailure(message: String) {

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

    override fun checkPlace(name: String, latitude: String, longitude: String) {
        placeRepository.checkPlace(
            name,
            latitude,
            longitude,
            object : PlaceCallback<PlaceDuplicateResponse> {
                override fun onSuccess(response: PlaceDuplicateResponse) {
                    view.showPlaceMessage(response.placeCheck)
                }

                override fun onFailure(message: String) {

                }

            })
    }
}