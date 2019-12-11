package com.yeonae.chamelezone.data.repository.place

interface PlaceRepository {
    fun registerPlace()
    fun getPlace()
    fun deletePlace()
    fun modifyPlace()
}