package tk.yeonaeyong.shopinshop.view.place.presenter

import tk.yeonaeyong.shopinshop.network.model.PlaceResponse

interface PlaceImageDetailContract {
    interface View {
        var presenter: Presenter
        fun showPlaceImage(place: PlaceResponse)

    }

    interface Presenter {
        fun getPlace(placeNumber: Int, memberNumber: Int?)
    }
}