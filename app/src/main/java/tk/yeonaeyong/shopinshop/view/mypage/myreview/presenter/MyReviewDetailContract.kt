package tk.yeonaeyong.shopinshop.view.mypage.myreview.presenter

import tk.yeonaeyong.shopinshop.data.model.ReviewItem

interface MyReviewDetailContract {
    interface View {
        var presenter: Presenter
        fun showMyReviewDetail(review: ReviewItem)
    }

    interface Presenter {
        fun getMyReviewDetail(placeNumber: Int, reviewNumber: Int)
    }
}