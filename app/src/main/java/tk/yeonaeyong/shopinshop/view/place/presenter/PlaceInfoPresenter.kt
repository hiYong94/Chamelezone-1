package tk.yeonaeyong.shopinshop.view.place.presenter

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceCallback
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepository
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

class PlaceInfoPresenter(
    private val placeRepository: PlaceRepository,
    private val memberRepository: MemberRepository,
    private val view: PlaceInfoContract.View
) : PlaceInfoContract.Presenter {
    override fun placeDetail(placeNumber: Int, memberNumber: Int?) {
        placeRepository.getPlaceDetail(
            placeNumber,
            memberNumber,
            object :
                PlaceCallback<PlaceResponse> {
                override fun onSuccess(response: PlaceResponse) {
                    view.placeInfo(response.toPlaceInfoItem())
                }

                override fun onFailure(message: String) {
                }

            })
    }

    override fun getUser() {
        memberRepository.getMember(object :
            MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                view.showUserInfo(response)
            }

            override fun onFailure(message: String) {
                view.getPlaceDetail()
            }

        })
    }
}