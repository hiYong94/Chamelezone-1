package tk.yeonaeyong.shopinshop.view.mypage.myreview.presenter

import tk.yeonaeyong.shopinshop.data.model.ReviewItem
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface MyReviewContract {
    interface View {
        var presenter: Presenter
        fun showMyReviewList(reviewList: List<ReviewItem>)
        fun getMember(user: UserEntity)
        fun getMemberCheck(response: Boolean)
        fun showReviewDelete(message: String)
    }

    interface Presenter {
        fun getUserReview(memberNumber: Int)
        fun getMember()
        fun checkMember()
        fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int)
    }
}