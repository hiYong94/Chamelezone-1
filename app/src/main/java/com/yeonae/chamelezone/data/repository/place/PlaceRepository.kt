package com.yeonae.chamelezone.data.repository.place

import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceResponse

interface PlaceRepository {
    fun registerPlace(
        keywordName: String,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String,
        latitude: String,
        longitude: String,
        callBack: PlaceCallBack<String>
    )

    fun getSearchByMap(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getSearchByName(name: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getSearchByAddress(address: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getSearchByKeyword(keyword: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getPlaceDetail(placeNumber: Int, callBack: PlaceCallBack<PlaceResponse>)
    fun getKeyword(callBack: PlaceCallBack<List<KeywordResponse>>)
    fun deletePlace()
    fun modifyPlace()
}