package tk.yeonaeyong.shopinshop.view.search.presenter

import tk.yeonaeyong.shopinshop.data.model.PlaceItem
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceCallback
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepository
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse

class SearchPresenter(
    private val placeRepository: PlaceRepository,
    private val searchView: SearchContract.View
) : SearchContract.Presenter {
    override fun searchByName(placeName: String) {
        placeRepository.getSearchByName(placeName, object :
            PlaceCallback<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                val placeItem = mutableListOf<PlaceItem>()
                for (i in response.indices) {
                    placeItem.add(response[i].toPlaceItem())
                }
                searchView.showPlaceList(placeItem)
            }

            override fun onFailure(message: String) {
                searchView.showMessage(message)
            }

        })
    }

    override fun searchByAddress(address: String) {
        placeRepository.getSearchByAddress(address, object :
            PlaceCallback<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                val placeItem = mutableListOf<PlaceItem>()
                for (i in response.indices) {
                    placeItem.add(response[i].toPlaceItem())
                }
                searchView.showPlaceList(placeItem)
            }

            override fun onFailure(message: String) {
                searchView.showMessage(message)
            }

        })
    }
}