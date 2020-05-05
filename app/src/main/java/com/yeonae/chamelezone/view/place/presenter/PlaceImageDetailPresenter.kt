package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallback
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse

class PlaceImageDetailPresenter(
    private val placeRepository: PlaceRepository,
    private val view: PlaceImageDetailContract.View
) : PlaceImageDetailContract.Presenter {
    override fun getPlace(placeNumber: Int, memberNumber: Int?) {
        placeRepository.getPlaceDetail(placeNumber, memberNumber, object : PlaceCallback<PlaceResponse> {
            override fun onSuccess(response: PlaceResponse) {
                view.showPlaceImage(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}