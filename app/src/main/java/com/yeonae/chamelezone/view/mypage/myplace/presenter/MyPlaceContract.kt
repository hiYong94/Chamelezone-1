package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface MyPlaceContract {

    interface View {
        var presenter: Presenter
        fun showMyPlaceList(response: List<PlaceResponse>)
        fun showUserInfo(user: UserEntity)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun getMyPlaceList(memberNumber: Int)
        fun getUser()
    }
}