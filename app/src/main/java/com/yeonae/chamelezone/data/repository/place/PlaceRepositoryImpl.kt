package com.yeonae.chamelezone.data.repository.place

import com.yeonae.chamelezone.data.source.remote.place.PlaceRemoteDataSource
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import java.math.BigDecimal

class PlaceRepositoryImpl private constructor(private val remoteDataSource: PlaceRemoteDataSource) :
    PlaceRepository {
    override fun registerPlace(
        memberNumber: Int,
        keywordName: List<Int>,
        name: String,
        address: String,
        openingTime: List<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        images: List<String>,
        callBack: PlaceCallBack<String>
    ) {
        remoteDataSource.registerPlace(
            memberNumber,
            keywordName,
            name,
            address,
            openingTime,
            phoneNumber,
            content,
            latitude,
            longitude,
            images,
            callBack
        )
    }

    override fun getSearchByMap(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        remoteDataSource.getSearchByMap(placeName, callBack)
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

    override fun getPlaceDetail(placeNumber: Int, memberNumber: Int, callBack: PlaceCallBack<PlaceResponse>) {
        remoteDataSource.getPlaceDetail(placeNumber, memberNumber, callBack)
    }

    override fun getMyPlaceList(memberNumber: Int, callBack: PlaceCallBack<List<PlaceResponse>>) {
        remoteDataSource.getMyPlaceList(memberNumber, callBack)
    }

    override fun getKeyword(callBack: PlaceCallBack<List<KeywordResponse>>) {
        remoteDataSource.getKeyword(callBack)
    }

    override fun modifyPlace() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePlace(placeNumber: Int, callBack: PlaceCallBack<String>) {
        remoteDataSource.deletePlace(placeNumber, callBack)
    }

    override fun getHomePlaceList(callBack: PlaceCallBack<List<PlaceResponse>>) {
        remoteDataSource.getHomePlaceList(callBack)
    }

    companion object {
        fun getInstance(remoteDataSource: PlaceRemoteDataSource): PlaceRepository =
            PlaceRepositoryImpl(remoteDataSource)
    }
}