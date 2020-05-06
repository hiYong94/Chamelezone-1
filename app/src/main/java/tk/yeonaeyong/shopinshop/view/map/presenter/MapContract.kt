package tk.yeonaeyong.shopinshop.view.map.presenter

import tk.yeonaeyong.shopinshop.data.model.MapItem

interface MapContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(placeList: List<MapItem>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun searchPlace(placeName: String)
    }
}