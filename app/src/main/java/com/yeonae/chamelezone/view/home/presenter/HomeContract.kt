package com.yeonae.chamelezone.view.home.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface HomeContract {

    interface View {
        var presenter: Presenter
        fun showHomeList(response: List<PlaceResponse>)
        fun getMember(user: UserEntity)
        fun getMemberCheck(response: Boolean)
    }
    interface Presenter {
        fun getHomeList(memberNumber: Int?)
        fun getMember()
        fun checkMember()
    }
}