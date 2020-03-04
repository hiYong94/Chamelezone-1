package com.yeonae.chamelezone.view.like.presenter

import com.yeonae.chamelezone.network.model.LikeResponse
import com.yeonae.chamelezone.network.model.PlaceResponse

interface LikeContract {
    interface View {
        var presenter: Presenter
        fun showResultView(response: Boolean)
        fun showLikeState(response: LikeResponse)
        fun showMyLikeList(response: List<PlaceResponse>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun checkLogin()
        fun getUser()
        fun deleteLike(memberNumber: Int, placeNumber: Int)
        fun getMyLikeList(memberNumber: Int)
    }
}