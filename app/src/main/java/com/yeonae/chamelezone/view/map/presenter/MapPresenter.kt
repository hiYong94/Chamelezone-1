package com.yeonae.chamelezone.view.map.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse

class MapPresenter(
    private val repository: PlaceRepository,
    private val view: MapContract.View
) : MapContract.Presenter {
    override fun searchPlace(placeName: String) {
        repository.getSearchByMap(placeName, object : PlaceCallBack<List<PlaceResponse>>{
            override fun onSuccess(response: List<PlaceResponse>) {
                view.placeInfo(response)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
            }

        })
    }
}