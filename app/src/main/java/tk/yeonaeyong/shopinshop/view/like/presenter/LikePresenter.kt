package tk.yeonaeyong.shopinshop.view.like.presenter

import tk.yeonaeyong.shopinshop.data.model.LikeItem
import tk.yeonaeyong.shopinshop.data.repository.like.LikeCallback
import tk.yeonaeyong.shopinshop.data.repository.like.LikeRepository
import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.network.model.LikeResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

class LikePresenter(
    private val memberRepository: MemberRepository,
    private val likeRepository: LikeRepository,
    private val view: LikeContract.View
) : LikeContract.Presenter {
    override fun checkLogin() {
        memberRepository.checkLogin(object :
            MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showResultView(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getUser() {
        memberRepository.getMember(object :
            MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                response.userNumber?.let { getMyLikeList(it) }
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deleteLike(memberNumber: Int, placeNumber: Int) {
        likeRepository.deleteLike(memberNumber, placeNumber, object :
            LikeCallback<LikeResponse> {
            override fun onSuccess(response: LikeResponse) {
                view.showLikeState(response.toLikeStatusItem())
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getMyLikeList(memberNumber: Int) {
        likeRepository.getMyLikeList(memberNumber, object :
            LikeCallback<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                val likeItemList = mutableListOf<LikeItem>()
                for (i in response.indices) {
                    likeItemList.add(response[i].toLikeItem())
                }
                view.showMyLikeList(likeItemList)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
            }

        })
    }
}