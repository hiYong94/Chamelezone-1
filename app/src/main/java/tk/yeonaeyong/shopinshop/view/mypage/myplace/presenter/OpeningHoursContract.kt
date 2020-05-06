package tk.yeonaeyong.shopinshop.view.mypage.myplace.presenter

interface OpeningHoursContract {

    interface View {
        var presenter: Presenter
        fun showResult(response: Boolean)
    }

    interface Presenter {
        fun updateOpeningHours(
            placeNumber: Int,
            openingTimes: List<String>
        )
    }
}