package com.yeonae.chamelezone.data.repository.place

import com.yeonae.chamelezone.data.source.remote.place.PlaceRemoteDataSource
import com.yeonae.chamelezone.network.model.PlaceResponse

class PlaceRepositoryImpl private constructor(private val remoteDataSource: PlaceRemoteDataSource) :
    PlaceRepository {
    override fun registerPlace(
        keywordName: String,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String,
        latitude: Double,
        longitude: Double,
        callBack: PlaceCallBack<String>
    ) {
        remoteDataSource.registerPlace(
            keywordName,
            name,
            address,
            openingTime,
            phoneNumber,
            content,
            latitude,
            longitude,
            callBack
        )
    }
    override fun searchPlace(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>){

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