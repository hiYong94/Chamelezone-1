package tk.yeonaeyong.shopinshop.view.map.presenter

import tk.yeonaeyong.shopinshop.data.model.MapItem
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceCallback
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepository
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse

class MarkerInfoPresenter(
    private val repository: PlaceRepository,
    private val view: MarkerInfoContract.View
) : MarkerInfoContract.Presenter {
    override fun searchPlace(placeName: String) {
        repository.getSearchByMap(placeName, object :
            PlaceCallback<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                val mapItem = mutableListOf<MapItem>()
                for (i in response.indices) {
                    mapItem.add(response[i].toMapItem())
                }
                view.placeInfo(mapItem)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}