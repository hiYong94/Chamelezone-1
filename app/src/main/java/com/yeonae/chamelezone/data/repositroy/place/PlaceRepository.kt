package com.yeonae.chamelezone.data.repositroy.place

import com.yeonae.chamelezone.data.PlaceCallback

interface PlaceRepository {
    fun getPlaceList(callback: PlaceCallback)
}