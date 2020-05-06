package tk.yeonaeyong.shopinshop.view.place.presenter

import tk.yeonaeyong.shopinshop.data.model.ReviewItem
import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.data.repository.review.ReviewCallback
import tk.yeonaeyong.shopinshop.data.repository.review.ReviewRepository
import tk.yeonaeyong.shopinshop.network.model.ReviewResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

class PlaceReviewPresenter(
    private val reviewRepository: ReviewRepository,
    private val memberRepository: MemberRepository,
    private val placeReviewView: PlaceReviewContract.View
) : PlaceReviewContract.Presenter {
    override fun placeDetailReview(placeNumber: Int) {
        reviewRepository.getReviewList(placeNumber, object :
            ReviewCallback<List<ReviewResponse>> {
            override fun onSuccess(response: List<ReviewResponse>) {
                val reviewItemList = arrayListOf<ReviewItem>()
                response.forEach {
                    it.toReviewItem().let { reviewItem -> reviewItemList.add(reviewItem) }
                }
                placeReviewView.showPlaceReview(reviewItemList)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deleteReview(placeNumber: Int, reviewNumber: Int, memberNumber: Int) {
        reviewRepository.deleteReview(
            placeNumber,
            reviewNumber,
            memberNumber,
            object :
                ReviewCallback<String> {
                override fun onSuccess(response: String) {
                    placeReviewView.showReviewDelete(response)
                }

                override fun onFailure(message: String) {

                }
            })
    }

    override fun getMember() {
        memberRepository.getMember(object :
            MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                placeReviewView.showMemberReview(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun checkMember() {
        memberRepository.checkLogin(object :
            MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                placeReviewView.getMemberCheck(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}