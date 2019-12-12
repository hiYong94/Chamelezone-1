package com.yeonae.chamelezone.data.source.remote.place

interface PlaceRemoteDataSource {
    fun registerPlace()
    fun getPlace()
    fun deletePlace()
    fun modifyPlace()
}