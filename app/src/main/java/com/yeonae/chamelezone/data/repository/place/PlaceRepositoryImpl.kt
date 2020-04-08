package com.yeonae.chamelezone.data.repository.place

import com.yeonae.chamelezone.data.source.remote.place.PlaceRemoteDataSource
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceDuplicateResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import java.math.BigDecimal

class PlaceRepositoryImpl private constructor(private val remoteDataSource: PlaceRemoteDataSource) :
    PlaceRepository {
    override fun registerPlace(
        memberNumber: Int,
        keywordNames: List<Int>,
        name: String,
        address: String,
        openingTimes: List<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        images: List<String>,
        callBack: PlaceCallBack<String>
    ) {
        remoteDataSource.registerPlace(
            memberNumber,
            keywordNames,
            name,
            address,
            openingTimes,
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

    override fun getPlaceDetail(
        placeNumber: Int,
        memberNumber: Int?,
        callBack: PlaceCallBack<PlaceResponse>
    ) {
        remoteDataSource.getPlaceDetail(placeNumber, memberNumber, callBack)
    }

    override fun getMyPlaceList(memberNumber: Int, callBack: PlaceCallBack<List<PlaceResponse>>) {
        remoteDataSource.getMyPlaceList(memberNumber, callBack)
    }

    override fun getKeyword(callBack: PlaceCallBack<List<KeywordResponse>>) {
        remoteDataSource.getKeyword(callBack)
    }

    override fun updatePlace(
        placeNumber: Int,
        images: List<String>,
        memberNumber: Int,
        address: String,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        imageNumber: List<Int>,
        callBack: PlaceCallBack<Boolean>
    ) {
        remoteDataSource.updatePlace(
            placeNumber,
            images,
            memberNumber,
            address,
            phoneNumber,
            content,
            latitude,
            longitude,
            imageNumber,
            callBack
        )
    }

    override fun updateKeyword(
        placeNumber: Int,
        keywordNames: List<Int>,
        placeKeywordNumber: List<Int>,
        callBack: PlaceCallBack<Boolean>
    ) {
        remoteDataSource.updateKeyword(placeNumber, keywordNames, placeKeywordNumber, callBack)
    }

    override fun updateOpeningHours(
        placeNumber: Int,
        openingTimes: List<String>,
        callBack: PlaceCallBack<Boolean>
    ) {
        remoteDataSource.updateOpeningHours(placeNumber, openingTimes, callBack)
    }

    override fun deletePlace(
        placeNumber: Int,
        memberNumber: Int,
        callBack: PlaceCallBack<Boolean>
    ) {
        remoteDataSource.deletePlace(placeNumber, memberNumber, callBack)
    }

    override fun getHomePlaceList(
        memberNumber: Int?,
        callBack: PlaceCallBack<List<PlaceResponse>>
    ) {
        remoteDataSource.getHomePlaceList(memberNumber, callBack)
    }

    override fun checkPlace(
        name: String,
        latitude: String,
        longitude: String,
        callBack: PlaceCallBack<PlaceDuplicateResponse>
    ) {
        remoteDataSource.checkPlace(name, latitude, longitude, callBack)
    }

    companion object {
        fun getInstance(remoteDataSource: PlaceRemoteDataSource): PlaceRepository =
            PlaceRepositoryImpl(remoteDataSource)
    }
}