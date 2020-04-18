package com.yeonae.chamelezone.view.map.presenter

import com.yeonae.chamelezone.data.model.MapItem
import com.yeonae.chamelezone.data.repository.place.PlaceCallback
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse

class MarkerInfoPresenter(
    private val repository: PlaceRepository,
    private val view: MarkerInfoContract.View
) : MarkerInfoContract.Presenter {
    override fun searchPlace(placeName: String) {
        repository.getSearchByMap(placeName, object : PlaceCallback<List<PlaceResponse>>{
            override fun onSuccess(response: List<PlaceResponse>) {
                val mapItem = mutableListOf<MapItem>()
                for (i in response.indices) {
                    mapItem.add(response[i].toMapItem())
                }
                view.placeInfo(mapItem)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}