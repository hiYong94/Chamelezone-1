package tk.yeonaeyong.shopinshop.view.mypage.myplace.presenter

import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import java.math.BigDecimal

interface PlaceModifyContract {

    interface View {
        var presenter: Presenter
        fun showPlaceDetail(place: PlaceResponse)
        fun showResult(response: Boolean)
    }

    interface Presenter {
        fun getPlaceDetail(placeNumber: Int, memberNumber: Int?)
        fun updatePlace(
            placeNumber: Int,
            insertImages: List<String>,
            deleteImageNumbers: List<Int>,
            memberNumber: Int,
            address: String,
            addressDetail: String,
            phoneNumber: String,
            content: String,
            latitude: BigDecimal,
            longitude: BigDecimal
        )

        fun updatePlace(
            placeNumber: Int,
            memberNumber: Int,
            address: String,
            addressDetail: String,
            phoneNumber: String,
            content: String,
            latitude: BigDecimal,
            longitude: BigDecimal
        )
    }
}