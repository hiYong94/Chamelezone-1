package tk.yeonaeyong.shopinshop.view.mypage.myreview.presenter

import tk.yeonaeyong.shopinshop.data.repository.review.ReviewCallback
import tk.yeonaeyong.shopinshop.data.repository.review.ReviewRepository
import tk.yeonaeyong.shopinshop.network.model.ReviewResponse

class MyReviewImageDetailPresenter(
    private val reviewRepository: ReviewRepository,
    private val reviewDetailView: MyReviewImageDetailContract.View
) : MyReviewImageDetailContract.Presenter {
    override fun getReview(placeNumber: Int, reviewNumber: Int) {
        reviewRepository.getReviewDetail(placeNumber, reviewNumber, object :
            ReviewCallback<ReviewResponse> {
            override fun onSuccess(response: ReviewResponse) {
                response.toReviewItem()?.let { reviewDetailView.showReviewImage(it) }
            }

            override fun onFailure(message: String) {

            }
        })
    }
}