package tk.yeonaeyong.shopinshop.view.mypage.myplace.presenter

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceCallback
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepository
import tk.yeonaeyong.shopinshop.network.model.KeywordResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceDuplicateResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity
import java.math.BigDecimal

class PlacePresenter(
    private val memberRepository: MemberRepository,
    private val placeRepository: PlaceRepository,
    private val view: PlaceContract.View
) : PlaceContract.Presenter {
    override fun placeRegister(
        memberNumber: Int,
        keywordName: List<Int>,
        name: String,
        address: String,
        addressDetail: String,
        openingTime: List<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        images: List<String>
    ) {
        placeRepository.registerPlace(
            memberNumber,
            keywordName,
            name,
            address,
            addressDetail,
            openingTime,
            phoneNumber,
            content,
            latitude,
            longitude,
            images,
            object :
                PlaceCallback<String> {
                override fun onSuccess(response: String) {
                    view.showMessage(response)
                }

                override fun onFailure(message: String) {

                }

            }
        )
    }

    override fun getKeyword() {
        placeRepository.getKeyword(object :
            PlaceCallback<List<KeywordResponse>> {
            override fun onSuccess(response: List<KeywordResponse>) {
                view.showKeywordList(response)
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

            }

        })
    }

    override fun checkPlace(name: String, latitude: String, longitude: String) {
        placeRepository.checkPlace(
            name,
            latitude,
            longitude,
            object :
                PlaceCallback<PlaceDuplicateResponse> {
                override fun onSuccess(response: PlaceDuplicateResponse) {
                    view.showPlaceMessage(response.placeCheck)
                }

                override fun onFailure(message: String) {

                }

            })
    }
}