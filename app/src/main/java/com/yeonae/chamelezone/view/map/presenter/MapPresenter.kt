package com.yeonae.chamelezone.view.map.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse

class MapPresenter(
    private val placeRepository: PlaceRepository,
    private val mapView: MapContract.View
) : MapContract.Presenter {
    override fun searchPlace(placeName: String) {
        placeRepository.getSearchByMap(placeName, object : PlaceCallBack<List<PlaceResponse>>{
            override fun onSuccess(response: List<PlaceResponse>) {
                mapView.placeInfo(response)
            }

            override fun onFailure(message: String) {
            }

        })
    }
}