package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.network.model.KeywordResponse

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
            placeKeywordNumber: List<Int>
        )
        fun getKeyword()
    }
}