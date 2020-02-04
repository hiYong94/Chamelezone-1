package com.yeonae.chamelezone.data.repository.place

import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import java.math.BigDecimal

interface PlaceRepository {
    fun registerPlace(
        keywordName: MutableList<Int>,
        name: String,
        address: String,
        openingTime: MutableList<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        callBack: PlaceCallBack<String>
    )

    fun getSearchByMap(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getSearchByName(name: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getSearchByAddress(address: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getSearchByKeyword(keyword: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getPlaceDetail(placeNumber: Int, callBack: PlaceCallBack<PlaceResponse>)
    fun getMyPlaceList(memberNumber: Int, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getKeyword(callBack: PlaceCallBack<List<KeywordResponse>>)
    fun modifyPlace()
    fun deletePlace(placeNumber: Int, callBack: PlaceCallBack<String>)
}