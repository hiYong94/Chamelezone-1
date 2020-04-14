package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallback
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import java.math.BigDecimal

class PlaceModifyPresenter(
    private val repository: PlaceRepository,
    private val view: PlaceModifyContract.View
) : PlaceModifyContract.Presenter {
    override fun getPlaceDetail(placeNumber: Int, memberNumber: Int?) {
        repository.getPlaceDetail(placeNumber, memberNumber, object : PlaceCallback<PlaceResponse> {
            override fun onSuccess(response: PlaceResponse) {
                view.showPlaceDetail(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getKeyword() {
        repository.getKeyword(object : PlaceCallback<List<KeywordResponse>> {
            override fun onSuccess(response: List<KeywordResponse>) {
                view.showKeywordList(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
    override fun updatePlace(
        placeNumber: Int,
        images: List<String>,
        memberNumber: Int,
        address: String,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        imageNumbers: List<Int>
    ) {
        repository.updatePlace(placeNumber, images, memberNumber, address, phoneNumber, content, latitude, longitude, imageNumbers, object : PlaceCallback<Boolean>{
            override fun onSuccess(response: Boolean) {
                view.showResult(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

}