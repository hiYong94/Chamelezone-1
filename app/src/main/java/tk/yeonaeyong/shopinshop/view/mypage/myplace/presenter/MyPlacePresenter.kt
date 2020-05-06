package tk.yeonaeyong.shopinshop.view.mypage.myplace.presenter

import tk.yeonaeyong.shopinshop.data.model.PlaceItem
import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceCallback
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepository
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

class MyPlacePresenter(
    private val memberRepository: MemberRepository,
    private val placeRepository: PlaceRepository,
    private val view: MyPlaceContract.View
) : MyPlaceContract.Presenter {
    override fun getMyPlaceList(memberNumber: Int) {
        placeRepository.getMyPlaceList(memberNumber, object :
            PlaceCallback<List<PlaceResponse>> {
            override fun onSuccess(response: List<PlaceResponse>) {
                val placeItem = mutableListOf<PlaceItem>()
                for (i in response.indices) {
                    placeItem.add(response[i].toPlaceItem())
                }
                view.showMyPlaceList(placeItem)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
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

            }

        })
    }

    override fun deletePlace(placeNumber: Int, memberNumber: Int) {
        placeRepository.deletePlace(placeNumber, memberNumber, object :
            PlaceCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showDeleteResult(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}