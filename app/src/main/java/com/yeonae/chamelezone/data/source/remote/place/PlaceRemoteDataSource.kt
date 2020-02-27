package com.yeonae.chamelezone.data.source.remote.place

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import java.math.BigDecimal

interface PlaceRemoteDataSource {
    fun registerPlace(
        memberNumber: Int,
        keywordNames: List<Int>,
        name: String,
        address: String,
        openingTimes: List<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        images: List<String>,
        callBack: PlaceCallBack<String>
    )

    fun getSearchByMap(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getSearchByName(name: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getSearchByAddress(address: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getSearchByKeyword(keyword: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getPlaceDetail(placeNumber: Int, memberNumber: Int?, callBack: PlaceCallBack<PlaceResponse>)
    fun getMyPlaceList(memberNumber: Int, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getKeyword(callBack: PlaceCallBack<List<KeywordResponse>>)
    fun modifyPlace(
        memberNumber: Int,
        keywordNames: List<Int>,
        name: String,
        address: String,
        openingTimes: List<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        images: List<String>,
        callBack: PlaceCallBack<Boolean>
    )

    fun deletePlace(placeNumber: Int, memberNumber: Int, callBack: PlaceCallBack<Boolean>)
    fun getHomePlaceList(callBack: PlaceCallBack<List<PlaceResponse>>)
//    fun getPlaceDetailReview(placeNumber: Int, callBack: PlaceCallBack<PlaceResponse>)
}