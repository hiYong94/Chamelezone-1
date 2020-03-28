package com.yeonae.chamelezone.view.search.presenter

import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse

class SearchPresenter(
    private val placeRepository: PlaceRepository,
    private val searchView: SearchContract.View
) : SearchContract.Presenter {
    override fun searchByName(placeName: String) {
        placeRepository.getSearchByName(placeName, object : PlaceCallBack<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                val placeItem = mutableListOf<PlaceItem>()
                for (i in response.indices) {
                    placeItem.add(response[i].toPlaceItem())
                }
                searchView.showPlaceList(placeItem)
            }

            override fun onFailure(message: String) {
                searchView.showMessage(message)
            }

        })
    }

    override fun searchByAddress(address: String) {
        placeRepository.getSearchByAddress(address, object : PlaceCallBack<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                val placeItem = mutableListOf<PlaceItem>()
                for (i in response.indices) {
                    placeItem.add(response[i].toPlaceItem())
                }
                searchView.showPlaceList(placeItem)
            }

            override fun onFailure(message: String) {
                searchView.showMessage(message)
            }

        })
    }
}