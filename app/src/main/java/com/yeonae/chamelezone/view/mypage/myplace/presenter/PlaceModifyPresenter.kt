package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceResponse

class PlaceModifyPresenter(
    private val repository: PlaceRepository,
    private val view: PlaceModifyContract.View
) : PlaceModifyContract.Presenter {
    override fun getPlaceDetail(placeNumber: Int, memberNumber: Int?) {
        repository.getPlaceDetail(placeNumber, memberNumber, object : PlaceCallBack<PlaceResponse> {
            override fun onSuccess(response: PlaceResponse) {
                view.showPlaceDetail(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getKeyword() {
        repository.getKeyword(object : PlaceCallBack<List<KeywordResponse>> {
            override fun onSuccess(response: List<KeywordResponse>) {
                view.showKeywordList(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}