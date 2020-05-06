package tk.yeonaeyong.shopinshop.view.place.presenter

import tk.yeonaeyong.shopinshop.data.repository.place.PlaceCallback
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepository
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse

class PlaceImageDetailPresenter(
    private val placeRepository: PlaceRepository,
    private val view: PlaceImageDetailContract.View
) : PlaceImageDetailContract.Presenter {
    override fun getPlace(placeNumber: Int, memberNumber: Int?) {
        placeRepository.getPlaceDetail(placeNumber, memberNumber, object :
            PlaceCallback<PlaceResponse> {
            override fun onSuccess(response: PlaceResponse) {
                view.showPlaceImage(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}