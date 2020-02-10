package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse

interface MyPlaceContract {

    interface View {
        var presenter: Presenter
        fun showMyPlaceList(response: List<PlaceResponse>)
    }

    interface Presenter {
        fun getMyPlaceList(memberNumber: Int)
    }
}