package tk.yeonaeyong.shopinshop.view.place.presenter

import tk.yeonaeyong.shopinshop.data.model.ReviewItem
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface PlaceReviewContract {
    interface View {
        var presenter: Presenter
        fun showPlaceReview(reviewList: List<ReviewItem>)
        fun showReviewDelete(message: String)
        fun showMemberReview(user: UserEntity)
        fun getMemberCheck(response: Boolean)
    }

    interface Presenter {
        fun placeDetailReview(placeNumber: Int)
        fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int)
        fun getMember()
        fun checkMember()
    }
}