package com.yeonae.chamelezone.view.like.presenter

import com.yeonae.chamelezone.data.model.LikeItem
import com.yeonae.chamelezone.data.model.LikeStatusItem

interface LikeContract {
    interface View {
        var presenter: Presenter
        fun showResultView(response: Boolean)
        fun showLikeState(response: LikeStatusItem)
        fun showMyLikeList(response: List<LikeItem>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun checkLogin()
        fun getUser()
        fun deleteLike(memberNumber: Int, placeNumber: Int)
        fun getMyLikeList(memberNumber: Int)
    }
}