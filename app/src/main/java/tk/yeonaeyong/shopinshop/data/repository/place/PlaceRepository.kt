package tk.yeonaeyong.shopinshop.data.repository.place

import tk.yeonaeyong.shopinshop.network.model.KeywordResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceDuplicateResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import java.math.BigDecimal

interface PlaceRepository {
    fun registerPlace(
        memberNumber: Int,
        keywordNames: List<Int>,
        name: String,
        address: String,
        addressDetail: String,
        openingTimes: List<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        images: List<String>,
        callback: PlaceCallback<String>
    )

    fun getSearchByMap(placeName: String, callback: PlaceCallback<List<PlaceResponse>>)
    fun getSearchByName(name: String, callback: PlaceCallback<List<PlaceResponse>>)
    fun getSearchByAddress(address: String, callback: PlaceCallback<List<PlaceResponse>>)
    fun getSearchByKeyword(keyword: String, callback: PlaceCallback<List<PlaceResponse>>)
    fun getPlaceDetail(placeNumber: Int, memberNumber: Int?, callback: PlaceCallback<PlaceResponse>)
    fun getMyPlaceList(memberNumber: Int, callback: PlaceCallback<List<PlaceResponse>>)
    fun getKeyword(callback: PlaceCallback<List<KeywordResponse>>)
    fun updatePlace(
        placeNumber: Int,
        insertImages: List<String>,
        deleteImageNumbers: List<Int>,
        memberNumber: Int,
        address: String,
        addressDetail: String,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        callback: PlaceCallback<Boolean>
    )

    fun updatePlace(
        placeNumber: Int,
        memberNumber: Int,
        address: String,
        addressDetail: String,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        callback: PlaceCallback<Boolean>
    )

    fun updateKeyword(
        placeNumber: Int,
        keywordNames: List<Int>,
        placeKeywordNumbers: List<Int>,
        callback: PlaceCallback<Boolean>
    )

    fun updateOpeningHours(
        placeNumber: Int,
        openingTimes: List<String>,
        callback: PlaceCallback<Boolean>
    )

    fun deletePlace(placeNumber: Int, memberNumber: Int, callback: PlaceCallback<Boolean>)
    fun getHomePlaceList(memberNumber: Int?, callback: PlaceCallback<List<PlaceResponse>>)
    fun checkPlace(
        name: String,
        latitude: String,
        longitude: String,
        callback: PlaceCallback<PlaceDuplicateResponse>
    )

    fun getKeywordRank(callback: PlaceCallback<List<KeywordResponse>>)
}