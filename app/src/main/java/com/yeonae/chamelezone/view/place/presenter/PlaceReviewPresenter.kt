package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.PlaceResponse

class PlaceReviewPresenter(private val placeRepository: PlaceRepository,
                           private val placeReviewView: PlaceReviewContract.View
) : PlaceReviewContract.Presenter {
    override fun placeDetailReview(placeNumber: Int) {
        placeRepository.getPlaceDetailReview(placeNumber, object : PlaceCallBack<PlaceResponse> {
            override fun onSuccess(response: PlaceResponse) {
                placeReviewView.placeReview(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}