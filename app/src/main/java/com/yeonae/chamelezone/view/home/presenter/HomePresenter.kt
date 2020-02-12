package com.yeonae.chamelezone.view.home.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse

class HomePresenter(
    private val repository: PlaceRepository,
    private val view: HomeContract.View
) : HomeContract.Presenter {
    override fun getHomeList() {
        repository.getHomePlaceList(object : PlaceCallBack<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                view.showHomeList(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}