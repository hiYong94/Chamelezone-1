package com.yeonae.chamelezone.data.sourse.place.local

import com.yeonae.chamelezone.data.PlaceCallback

interface PlaceLocalDataSource {
    fun getPlaceDataList(callback: PlaceCallback)
}