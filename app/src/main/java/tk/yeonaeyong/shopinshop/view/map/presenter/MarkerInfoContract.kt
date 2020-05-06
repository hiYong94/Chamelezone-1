package tk.yeonaeyong.shopinshop.view.map.presenter

import tk.yeonaeyong.shopinshop.data.model.MapItem

interface MarkerInfoContract {

    interface View {
        var presenter: Presenter
        fun placeInfo(placeList: List<MapItem>)
    }

    interface Presenter {
        fun searchPlace(placeName: String)
    }
}