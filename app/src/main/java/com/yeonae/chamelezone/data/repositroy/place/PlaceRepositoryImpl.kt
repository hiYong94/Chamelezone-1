package com.yeonae.chamelezone.data.repositroy.place

import com.yeonae.chamelezone.data.PlaceCallback
import com.yeonae.chamelezone.data.sourse.place.local.PlaceLocalDataSource
import com.yeonae.chamelezone.data.sourse.place.remote.PlaceRemoteDataSource

class PlaceRepositoryImpl private constructor(
    private val placeRemoteDataSource: PlaceRemoteDataSource,
    private val placeLocalDataSource: PlaceLocalDataSource
) : PlaceRepository {
    override fun getPlaceList(callback: PlaceCallback) {
        placeLocalDataSource.getPlaceDataList(callback)
    }
}