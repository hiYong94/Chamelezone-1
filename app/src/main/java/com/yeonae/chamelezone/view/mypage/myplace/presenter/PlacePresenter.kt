package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.KeywordResponse
import java.math.BigDecimal

class PlacePresenter(
    private val placeRepository: PlaceRepository,
    private val placeView: PlaceContract.View
) : PlaceContract.Presenter {
    override fun placeRegister(
        keywordName: MutableList<Int>,
        name: String,
        address: String,
        openingTime: MutableList<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal
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
                    placeView.showMessage(message)
                }

                override fun onFailure(message: String) {

                }

            }
        )
    }

    override fun getKeyword() {
        placeRepository.getKeyword(object : PlaceCallBack<List<KeywordResponse>> {
            override fun onSuccess(response: List<KeywordResponse>) {
                placeView.showKeywordList(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}