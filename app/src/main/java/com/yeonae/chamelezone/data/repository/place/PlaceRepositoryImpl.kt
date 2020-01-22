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
        latitude: String,
        longitude: String,
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

    override fun getSearchByMap(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        remoteDataSource.searchPlace(placeName, callBack)
    }

    override fun getSearchByName(name: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        remoteDataSource.getSearchByName(name, callBack)
    }

    override fun getSearchByAddress(address: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        remoteDataSource.getSearchByAddress(address, callBack)
    }

    override fun getSearchByKeyword(keyword: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        remoteDataSource.getSearchByKeyword(keyword, callBack)
    }

    override fun getPlaceDetail(placeNumber: String, callBack: PlaceCallBack<PlaceResponse>) {
        remoteDataSource.getPlaceDetail(placeNumber, callBack)
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