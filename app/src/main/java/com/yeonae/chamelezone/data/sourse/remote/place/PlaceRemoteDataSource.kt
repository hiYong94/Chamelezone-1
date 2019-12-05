package com.yeonae.chamelezone.data.sourse.remote.place

interface PlaceRemoteDataSource {
    fun registerPlace()
    fun getPlace()
    fun deletePlace()
    fun modifyPlace()
}