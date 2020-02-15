package com.yeonae.chamelezone.view.home.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse

interface HomeContract {

    interface View {
        var presenter: Presenter
        fun showHomeList(response: List<PlaceResponse>)
    }
    interface Presenter {
        fun getHomeList()
    }
}