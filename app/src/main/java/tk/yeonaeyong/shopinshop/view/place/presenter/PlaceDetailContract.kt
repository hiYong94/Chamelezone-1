package tk.yeonaeyong.shopinshop.view.place.presenter

import tk.yeonaeyong.shopinshop.data.model.LikeStatusItem
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface PlaceDetailContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(place: PlaceResponse)
        fun showResultView(response: Boolean)
        fun deliverUserInfo(user: UserEntity)
        fun showLikeMessage(response: LikeStatusItem)
        fun showDeleteLikeMessage(response: LikeStatusItem)
    }

    interface Presenter {
        fun placeDetail(placeNumber: Int, memberNumber: Int?)
        fun checkLogin()
        fun getUser()
        fun selectLike(memberNumber: Int, placeNumber: Int)
        fun deleteLike(memberNumber: Int, placeNumber: Int)
    }
}