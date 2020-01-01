package com.yeonae.chamelezone.data.repository.place

import com.yeonae.chamelezone.data.source.remote.place.PlaceRemoteDataSource

class PlaceRepositoryImpl private constructor(private val remoteDataSource: PlaceRemoteDataSource) :
    PlaceRepository {
    override fun registerPlace(
        keywordNumber: Int,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String
    ) {
        remoteDataSource.registerPlace(
            keywordNumber,
            name,
            address,
            openingTime,
            phoneNumber,
            content
        )
    }

    override fun getPlace() {

    }

    override fun deletePlace() {

    }

    override fun modifyPlace() {

    }


    companion object {
        fun getInstance(remoteDataSource: PlaceRemoteDataSource): PlaceRepository =
            PlaceRepositoryImpl(remoteDataSource)
    }
}