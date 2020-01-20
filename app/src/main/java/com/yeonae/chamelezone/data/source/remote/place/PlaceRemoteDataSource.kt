package com.yeonae.chamelezone.data.source.remote.place

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.network.model.PlaceResponse

interface PlaceRemoteDataSource {
    fun registerPlace(
        keywordName: String,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String,
        latitude: Double,
        longitude: Double,
        callBack: PlaceCallBack<String>
    )

    fun searchPlace(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>)
    fun getPlace()
    fun deletePlace()
    fun modifyPlace()
}