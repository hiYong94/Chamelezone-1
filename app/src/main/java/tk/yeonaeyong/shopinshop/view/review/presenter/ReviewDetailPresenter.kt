package tk.yeonaeyong.shopinshop.view.review.presenter

import tk.yeonaeyong.shopinshop.data.repository.review.ReviewCallback
import tk.yeonaeyong.shopinshop.data.repository.review.ReviewRepository
import tk.yeonaeyong.shopinshop.network.model.ReviewResponse

class ReviewDetailPresenter(
    private val reviewRepository: ReviewRepository,
    private val reviewDetailView: ReviewDetailContract.View
) : ReviewDetailContract.Presenter {
    override fun getReview(placeNumber: Int, reviewNumber: Int) {
        reviewRepository.getReviewDetail(placeNumber, reviewNumber, object :
            ReviewCallback<ReviewResponse> {
            override fun onSuccess(response: ReviewResponse) {
                reviewDetailView.showReviewImage(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}