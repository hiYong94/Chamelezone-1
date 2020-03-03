package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface PlaceInfoContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(place: PlaceResponse)
        fun showUserInfo(user: UserEntity)
        fun getPlaceDetail()
    }

    interface Presenter {
        fun placeDetail(placeNumber: Int, memberNumber: Int?)
        fun getUser()
    }
}