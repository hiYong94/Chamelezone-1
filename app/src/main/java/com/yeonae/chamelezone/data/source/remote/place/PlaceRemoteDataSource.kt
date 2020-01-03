package com.yeonae.chamelezone.data.source.remote.place

import com.yeonae.chamelezone.data.repository.place.PlaceCallBack

interface PlaceRemoteDataSource {
    fun registerPlace(
        keywordNumber: Int,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String,
        callBack: PlaceCallBack
    )

    fun getPlace()
    fun deletePlace()
    fun modifyPlace()
}