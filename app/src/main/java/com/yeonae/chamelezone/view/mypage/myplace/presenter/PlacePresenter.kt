package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.KeywordResponse

class PlacePresenter(
    private val placeRepository: PlaceRepository,
    private val placeView: PlaceContract.View
) : PlaceContract.Presenter {
    override fun getKeyword() {
        placeRepository.getKeyword(object : PlaceCallBack<List<KeywordResponse>> {
            override fun onSuccess(response: List<KeywordResponse>) {
                placeView.showKeywordList(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun placeRegister(
        keywordName: String,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String,
        latitude: String,
        longitude: String
    ) {
        placeRepository.registerPlace(
            keywordName,
            name,
            address,
            openingTime,
            phoneNumber,
            content,
            latitude,
            longitude,
            object : PlaceCallBack<String> {
                override fun onSuccess(message: String) {
                    placeView.place(message)
                }

                override fun onFailure(message: String) {

                }

            }
        )
    }
}