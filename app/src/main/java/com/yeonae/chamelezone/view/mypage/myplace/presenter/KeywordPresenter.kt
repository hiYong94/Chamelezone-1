package com.yeonae.chamelezone.view.mypage.myplace.presenter

import com.yeonae.chamelezone.data.repository.place.PlaceCallback
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.network.model.KeywordResponse

class KeywordPresenter(
    private val repository: PlaceRepository,
    private val view: KeywordContract.View
) : KeywordContract.Presenter {
    override fun updateKeyword(
        placeNumber: Int,
        keywordNames: List<Int>,
        placeKeywordNumbers: List<Int>
    ) {
        repository.updateKeyword(
            placeNumber,
            keywordNames,
            placeKeywordNumbers,
            object : PlaceCallback<Boolean> {
                override fun onSuccess(response: Boolean) {
                    view.showResult(response)
                }

                override fun onFailure(message: String) {

                }

            })
    }

    override fun getKeyword() {
        repository.getKeyword(object : PlaceCallback<List<KeywordResponse>> {
            override fun onSuccess(response: List<KeywordResponse>) {
                view.showKeywordList(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}