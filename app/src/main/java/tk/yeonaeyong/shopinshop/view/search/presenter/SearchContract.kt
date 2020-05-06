package tk.yeonaeyong.shopinshop.view.search.presenter

import tk.yeonaeyong.shopinshop.data.model.PlaceItem

interface SearchContract {

    interface View {
        var presenter: Presenter
        fun showPlaceList(placeList: List<PlaceItem>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun searchByName(placeName: String)
        fun searchByAddress(address: String)
    }
}