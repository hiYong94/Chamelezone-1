package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse

class PlaceInfoPresenter(
    private val placeRepository: PlaceRepository,
    private val placeInfoView: PlaceInfoContract.View
) : PlaceInfoContract.Presenter {
    override fun placeDetail(placeNumber: Int) {
        placeRepository.getPlaceDetail(placeNumber, object : PlaceCallBack<PlaceResponse>{
            override fun onSuccess(response: PlaceResponse) {
                placeInfoView.placeInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}