package tk.yeonaeyong.shopinshop.view.place.presenter

import tk.yeonaeyong.shopinshop.data.repository.like.LikeCallback
import tk.yeonaeyong.shopinshop.data.repository.like.LikeRepository
import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceCallback
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepository
import tk.yeonaeyong.shopinshop.network.model.LikeResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

class PlaceDetailPresenter(
    private val memberRepository: MemberRepository,
    private val placeRepository: PlaceRepository,
    private val likeRepository: LikeRepository,
    private val view: PlaceDetailContract.View
) : PlaceDetailContract.Presenter {
    override fun placeDetail(placeNumber: Int, memberNumber: Int?) {
        placeRepository.getPlaceDetail(placeNumber, memberNumber, object :
            PlaceCallback<PlaceResponse> {
            override fun onSuccess(response: PlaceResponse) {
                view.placeInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

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
                view.deliverUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun selectLike(memberNumber: Int, placeNumber: Int) {
        likeRepository.selectLike(memberNumber, placeNumber, object :
            LikeCallback<LikeResponse> {
            override fun onSuccess(response: LikeResponse) {
                view.showLikeMessage(response.toLikeStatusItem())
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deleteLike(memberNumber: Int, placeNumber: Int) {
        likeRepository.deleteLike(memberNumber, placeNumber, object :
            LikeCallback<LikeResponse> {
            override fun onSuccess(response: LikeResponse) {
                view.showDeleteLikeMessage(response.toLikeStatusItem())
            }

            override fun onFailure(message: String) {

            }

        })
    }
}