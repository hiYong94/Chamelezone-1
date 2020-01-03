package com.yeonae.chamelezone.data.repository.place

interface PlaceRepository {
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