package com.yeonae.chamelezone.data.repository.place

import com.yeonae.chamelezone.data.source.remote.place.PlaceRemoteDataSource

class PlaceRepositoryImpl private constructor(
    private val placeRemoteDataSource: PlaceRemoteDataSource
) : PlaceRepository {

}