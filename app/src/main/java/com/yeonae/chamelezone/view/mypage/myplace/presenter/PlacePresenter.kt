package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository

class PlacePresenter(
    private val placeRepository: PlaceRepository,
    private val placeView: PlaceContract.View
) : PlaceContract.Presenter {
    override fun placeRegister(
        keywordNumber: Int,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String
    ) {
        placeRepository.registerPlace(
            keywordNumber,
            name,
            address,
            openingTime,
            phoneNumber,
            content,
            object : PlaceCallBack{
                override fun onSuccess(message: String) {
                    placeView.place(message)
                }

                override fun onFailure(message: String) {

                }

            }
        )
    }
}