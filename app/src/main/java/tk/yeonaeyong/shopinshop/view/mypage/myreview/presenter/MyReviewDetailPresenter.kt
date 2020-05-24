package tk.yeonaeyong.shopinshop.view.mypage.myreview.presenter

import tk.yeonaeyong.shopinshop.data.repository.review.ReviewCallback
import tk.yeonaeyong.shopinshop.data.repository.review.ReviewRepository
import tk.yeonaeyong.shopinshop.network.model.ReviewResponse

class MyReviewDetailPresenter(
    private val reviewRepository: ReviewRepository,
    private val myReviewDetailView: MyReviewDetailContract.View
) : MyReviewDetailContract.Presenter {
    override fun getMyReviewDetail(placeNumber: Int, reviewNumber: Int) {
        reviewRepository.getMyReviewDetail(
            placeNumber,
            reviewNumber,
            object :
                ReviewCallback<ReviewResponse> {
                override fun onSuccess(response: ReviewResponse) {
                    response.toReviewItem()?.let { myReviewDetailView.showMyReviewDetail(it) }
                }

                override fun onFailure(message: String) {

                }
            })
    }
}