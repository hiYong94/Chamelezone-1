package tk.yeonaeyong.shopinshop.view.review.presenter

import tk.yeonaeyong.shopinshop.data.model.ReviewItem

interface ReviewModifyContract {
    interface View {
        var presenter: Presenter
        fun reviewModify(response: Boolean)
        fun showReview(review: ReviewItem)
    }

    interface Presenter {
        fun modifyReview(
            images: List<String>,
            reviewNumber: Int,
            memberNumber: Int,
            placeNumber: Int,
            content: String,
            deleteImageNumber: List<Int>
        )

        fun getReview(placeNumber: Int, reviewNumber: Int)
    }
}