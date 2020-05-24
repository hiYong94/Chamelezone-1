package tk.yeonaeyong.shopinshop.network.model

import com.google.gson.annotations.SerializedName
import tk.yeonaeyong.shopinshop.data.model.LikeItem
import tk.yeonaeyong.shopinshop.data.model.MapItem
import tk.yeonaeyong.shopinshop.data.model.PlaceInfoItem
import tk.yeonaeyong.shopinshop.data.model.PlaceItem
import tk.yeonaeyong.shopinshop.ext.Url.IMAGE_RESOURCE

data class PlaceResponse(
    @SerializedName("placeNumber")
    val placeNumber: Int,
    @SerializedName("keywordName")
    val keywordName: ArrayList<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("addressDetail")
    var addressDetail: String,
    @SerializedName("openingTime")
    val openingTime: ArrayList<String>,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("savedImageName")
    val savedImageName: ArrayList<String>,
    @SerializedName("regiDate")
    val regiDate: String,
    @SerializedName("memberNumber")
    val memberNumber: Int,
    @SerializedName("likeStatus")
    val likeStatus: Boolean,
    @SerializedName("placeKeywordNumber")
    val placeKeywordNumbers: ArrayList<Int>,
    @SerializedName("imageNumber")
    val imageNumbers: ArrayList<Int>
) {
    fun toPlaceItem(): PlaceItem {
        var keywordFormat = ""
        keywordName.forEach {
            keywordFormat = if (it == keywordName[0]) {
                it
            } else {
                "${keywordFormat}${","} $it"
            }
        }
        val imageFormat = IMAGE_RESOURCE + savedImageName[0]
        if (addressDetail == null) {
            addressDetail = ""
        }
        val realAddress = "$address $addressDetail"
        return PlaceItem(
            placeNumber,
            name,
            keywordFormat,
            realAddress,
            imageFormat
        )
    }

    fun toLikeItem(): LikeItem {
        var keywordFormat = ""
        keywordName.forEach {
            keywordFormat = if (it == keywordName[0]) {
                it
            } else {
                "${keywordFormat}${","} $it"
            }
        }
        if (addressDetail == null) {
            addressDetail = ""
        }
        val realAddress = "$address $addressDetail"
        val image = IMAGE_RESOURCE + savedImageName[0]
        return LikeItem(
            placeNumber,
            memberNumber,
            name,
            keywordFormat,
            realAddress,
            image
        )
    }

    fun toMapItem(): MapItem {
        var keywordFormat = ""
        keywordName.forEach {
            keywordFormat = if (it == keywordName[0]) {
                it
            } else {
                "${keywordFormat}, $it"
            }
        }
        if (addressDetail == null) {
            addressDetail = ""
        }
        val realAddress = "$address $addressDetail"
        val image = IMAGE_RESOURCE + savedImageName[0]
        return MapItem(
            placeNumber,
            name,
            keywordFormat,
            realAddress,
            image,
            latitude,
            longitude
        )
    }

    fun toPlaceInfoItem(): PlaceInfoItem {
        var keywordFormat = ""
        keywordName.forEach {
            keywordFormat = if (it == keywordName[0]) {
                it
            } else {
                "${keywordFormat}${","} $it"
            }
        }
        if (addressDetail == null) {
            addressDetail = ""
        }
        val realAddress = "$address $addressDetail"
        val image = IMAGE_RESOURCE + savedImageName[0]
        var openingHours = ""
        openingTime.forEach {
            openingHours = if (it == openingTime[0]) {
                it
            } else {
                "${openingHours}\n$it"
            }
        }
        return PlaceInfoItem(
            placeNumber,
            name,
            keywordFormat,
            realAddress,
            openingHours,
            phoneNumber,
            content,
            image,
            latitude,
            longitude
        )
    }

}