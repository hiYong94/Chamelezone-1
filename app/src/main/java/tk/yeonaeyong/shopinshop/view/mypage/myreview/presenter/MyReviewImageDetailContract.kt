package tk.yeonaeyong.shopinshop.view.mypage.myreview.presenter

import tk.yeonaeyong.shopinshop.data.model.ReviewItem

interface MyReviewImageDetailContract {
    interface View {
        var presenter: Presenter
        fun showReviewImage(review: ReviewItem)

    }

    interface Presenter {
        fun getReview(placeNumber: Int, reviewNumber: Int)
    }
}