package com.yeonae.chamelezone.data.source.remote.place

interface PlaceRemoteDataSource {
    fun registerPlace(
        keywordNumber: Int,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String
    )

    fun getPlace()
    fun deletePlace()
    fun modifyPlace()
}