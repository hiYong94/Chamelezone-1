package tk.yeonaeyong.shopinshop.view.review.presenter

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.data.repository.review.ReviewCallback
import tk.yeonaeyong.shopinshop.data.repository.review.ReviewRepository
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

class ReviewPresenter(
    private val reviewRepository: ReviewRepository,
    private val memberRepository: MemberRepository,
    private val reviewView: ReviewContract.View
) : ReviewContract.Presenter {
    override fun reviewCreate(
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        images: List<String>
    ) {
        reviewRepository.createReview(
            memberNumber,
            placeNumber,
            content,
            images,
            object :
                ReviewCallback<String> {
                override fun onSuccess(response: String) {
                    reviewView.review(response)
                }

                override fun onFailure(message: String) {

                }
            })
    }

    override fun getMember() {
        memberRepository.getMember(object :
            MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                reviewView.getMember(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun checkMember() {
        memberRepository.checkLogin(object :
            MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                reviewView.getMemberCheck(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}