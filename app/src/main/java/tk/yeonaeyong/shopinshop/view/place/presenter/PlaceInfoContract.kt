package tk.yeonaeyong.shopinshop.view.place.presenter

import tk.yeonaeyong.shopinshop.data.model.PlaceInfoItem
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface PlaceInfoContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(place: PlaceInfoItem)
        fun showUserInfo(user: UserEntity)
        fun getPlaceDetail()
    }

    interface Presenter {
        fun placeDetail(placeNumber: Int, memberNumber: Int?)
        fun getUser()
    }
}