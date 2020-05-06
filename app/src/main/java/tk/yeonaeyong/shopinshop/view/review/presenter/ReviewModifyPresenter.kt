package tk.yeonaeyong.shopinshop.view.review.presenter

import tk.yeonaeyong.shopinshop.data.repository.review.ReviewCallback
import tk.yeonaeyong.shopinshop.data.repository.review.ReviewRepository
import tk.yeonaeyong.shopinshop.network.model.ReviewResponse

class ReviewModifyPresenter(
    private val reviewRepository: ReviewRepository,
    private val reviewView: ReviewModifyContract.View
) : ReviewModifyContract.Presenter {
    override fun modifyReview(
        images: List<String>,
        reviewNumber: Int,
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        deleteImageNumber: List<Int>
    ) {
        reviewRepository.updateReview(
            images,
            reviewNumber,
            memberNumber,
            placeNumber,
            content,
            deleteImageNumber,
            object :
                ReviewCallback<Boolean> {
                override fun onSuccess(response: Boolean) {
                    reviewView.reviewModify(response)
                }

                override fun onFailure(message: String) {

                }
            }
        )
    }

    override fun getReview(placeNumber: Int, reviewNumber: Int) {
        reviewRepository.getReviewDetail(placeNumber, reviewNumber, object :
            ReviewCallback<ReviewResponse> {
            override fun onSuccess(response: ReviewResponse) {
                response.toReviewItem().let { reviewView.showReview(it) }
            }

            override fun onFailure(message: String) {

            }
        })
    }
}