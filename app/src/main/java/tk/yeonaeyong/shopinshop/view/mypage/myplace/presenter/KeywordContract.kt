package tk.yeonaeyong.shopinshop.view.mypage.myplace.presenter

import tk.yeonaeyong.shopinshop.network.model.KeywordResponse

interface KeywordContract {

    interface View {
        var presenter: Presenter
        fun showKeywordList(response: List<KeywordResponse>)
        fun showResult(response: Boolean)
    }

    interface Presenter {
        fun updateKeyword(
            placeNumber: Int,
            keywordNames: List<Int>,
            placeKeywordNumbers: List<Int>
        )
        fun getKeyword()
    }
}