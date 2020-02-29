package com.yeonae.chamelezone.view.like.presenter

import com.yeonae.chamelezone.network.model.PlaceResponse

interface LikeContract {
    interface View {
        var presenter: Presenter
        fun showResultView(response: Boolean)
        fun showLikeState(response: Boolean)
        fun showMyLikeList(response: List<PlaceResponse>)
    }

    interface Presenter {
        fun checkLogin()
        fun getUser()
        fun deleteLike(likeNumber: Int, memberNumber: Int, placeNumber: Int)
        fun getMyLikeList(memberNumber: Int)
    }
}