package tk.yeonaeyong.shopinshop.view.mypage.myplace.presenter

import tk.yeonaeyong.shopinshop.network.model.KeywordResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity
import java.math.BigDecimal

interface PlaceContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
        fun showKeywordList(response: List<KeywordResponse>)
        fun showUserInfo(user: UserEntity)
        fun showPlaceMessage(placeCheck: String)
    }

    interface Presenter {
        fun placeRegister(
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
        )

        fun getKeyword()
        fun getUser()
        fun checkPlace(name: String, latitude: String, longitude: String)
    }
}