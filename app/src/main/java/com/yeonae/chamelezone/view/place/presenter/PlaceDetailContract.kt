package com.yeonae.chamelezone.view.place.presenter

import com.yeonae.chamelezone.network.model.LikeResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface PlaceDetailContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(place: PlaceResponse)
        fun showResultView(response: Boolean)
        fun deliverUserInfo(user: UserEntity)
        fun showLikeMessage(response: LikeResponse)
        fun showDeleteLikeMessage(response: LikeResponse)
    }

    interface Presenter {
        fun placeDetail(placeNumber: Int, memberNumber: Int?)
        fun checkLogin()
        fun getUser()
        fun selectLike(memberNumber: Int, placeNumber: Int)
        fun deleteLike(memberNumber: Int, placeNumber: Int)
    }
}