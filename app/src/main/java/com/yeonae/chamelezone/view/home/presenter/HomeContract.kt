package com.yeonae.chamelezone.view.home.presenter

import com.yeonae.chamelezone.data.model.LikeStatusItem
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface HomeContract {

    interface View {
        var presenter: Presenter
        fun showHomeList(place: List<PlaceResponse>)
        fun getMember(user: UserEntity)
        fun getMemberCheck(response: Boolean)
        fun showLikeMessage(response: LikeStatusItem)
        fun showDeleteLikeMessage(response: LikeStatusItem)
    }
    interface Presenter {
        fun getHomeList(memberNumber: Int?)
        fun getMember()
        fun checkMember()
        fun selectLike(memberNumber: Int, placeNumber: Int)
        fun deleteLike(memberNumber: Int, placeNumber: Int)
    }
}