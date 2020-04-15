package com.yeonae.chamelezone.data.source.remote.place

import com.yeonae.chamelezone.data.repository.place.PlaceCallback
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceDuplicateResponse
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
        images: List<String>,
        memberNumber: Int,
        address: String,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        imageNumbers: List<Int>,
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
}