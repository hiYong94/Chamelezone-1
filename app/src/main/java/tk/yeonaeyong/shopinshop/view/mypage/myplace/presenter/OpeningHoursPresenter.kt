package tk.yeonaeyong.shopinshop.view.mypage.myplace.presenter

import tk.yeonaeyong.shopinshop.data.repository.place.PlaceCallback
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepository

class OpeningHoursPresenter(
    private val repository: PlaceRepository,
    private val view: OpeningHoursContract.View
) : OpeningHoursContract.Presenter {
    override fun updateOpeningHours(placeNumber: Int, openingTimes: List<String>) {
        repository.updateOpeningHours(placeNumber, openingTimes, object :
            PlaceCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showResult(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}