package tk.yeonaeyong.shopinshop.view.review.presenter

import tk.yeonaeyong.shopinshop.network.model.ReviewResponse

interface ReviewDetailContract {
    interface View {
        var presenter: Presenter
        fun showReviewImage(review: ReviewResponse)

    }

    interface Presenter {
        fun getReview(placeNumber: Int, reviewNumber: Int)
    }
}