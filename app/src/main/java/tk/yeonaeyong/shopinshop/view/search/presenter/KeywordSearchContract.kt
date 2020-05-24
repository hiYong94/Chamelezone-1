package tk.yeonaeyong.shopinshop.view.search.presenter

import tk.yeonaeyong.shopinshop.data.model.PlaceItem
import tk.yeonaeyong.shopinshop.network.model.KeywordResponse

interface KeywordSearchContract {

    interface View {
        var presenter: Presenter
        fun showKeywordList(response: List<KeywordResponse>)
        fun showPlaceList(placeList: List<PlaceItem>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun searchByKeyword(keyword: String)
        fun getKeywordRank()
    }
}