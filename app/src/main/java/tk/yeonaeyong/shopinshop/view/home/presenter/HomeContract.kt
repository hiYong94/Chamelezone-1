package tk.yeonaeyong.shopinshop.view.home.presenter

import tk.yeonaeyong.shopinshop.data.model.LikeStatusItem
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface HomeContract {

    interface View {
        var presenter: Presenter
        fun showHomeList(placeList: List<PlaceResponse>)
        fun getMember(user: UserEntity)
        fun getMemberCheck(response: Boolean)
        fun showLikeMessage(likeStatusItem: LikeStatusItem)
        fun showDeleteLikeMessage(likeStatusItem: LikeStatusItem)
    }

    interface Presenter {
        fun getHomeList(memberNumber: Int?)
        fun getMember()
        fun checkMember()
        fun selectLike(memberNumber: Int, placeNumber: Int)
        fun deleteLike(memberNumber: Int, placeNumber: Int)
    }
}