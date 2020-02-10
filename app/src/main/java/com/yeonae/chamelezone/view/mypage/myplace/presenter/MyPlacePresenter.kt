package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse

class MyPlacePresenter(
    private val repository: PlaceRepository,
    private val view: MyPlaceContract.View
) : MyPlaceContract.Presenter {
    override fun getMyPlaceList(memberNumber: Int) {
        repository.getMyPlaceList(memberNumber, object : PlaceCallBack<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                view.showMyPlaceList(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}