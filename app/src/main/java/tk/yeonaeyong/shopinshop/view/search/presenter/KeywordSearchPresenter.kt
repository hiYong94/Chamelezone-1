package tk.yeonaeyong.shopinshop.view.search.presenter

import tk.yeonaeyong.shopinshop.data.model.PlaceItem
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceCallback
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepository
import tk.yeonaeyong.shopinshop.network.model.KeywordResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse

class KeywordSearchPresenter(
    private val repository: PlaceRepository,
    private val view: KeywordSearchContract.View
) : KeywordSearchContract.Presenter {
    override fun getKeywordRank() {
        repository.getKeywordRank(object :
            PlaceCallback<List<KeywordResponse>> {
            override fun onSuccess(response: List<KeywordResponse>) {
                view.showKeywordList(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun searchByKeyword(keyword: String) {
        repository.getSearchByKeyword(keyword, object :
            PlaceCallback<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                val placeItem = mutableListOf<PlaceItem>()
                for (i in response.indices) {
                    placeItem.add(response[i].toPlaceItem())
                }
                view.showPlaceList(placeItem)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
            }

        })
    }
}