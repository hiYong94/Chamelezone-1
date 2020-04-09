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
        callback: PlaceCallback<String>
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
            callback
        )
    }

    override fun getSearchByMap(placeName: String, callback: PlaceCallback<List<PlaceResponse>>) {
        remoteDataSource.getSearchByMap(placeName, callback)
    }

    override fun getSearchByName(name: String, callback: PlaceCallback<List<PlaceResponse>>) {
        remoteDataSource.getSearchByName(name, callback)
    }

    override fun getSearchByAddress(address: String, callback: PlaceCallback<List<PlaceResponse>>) {
        remoteDataSource.getSearchByAddress(address, callback)
    }

    override fun getSearchByKeyword(keyword: String, callback: PlaceCallback<List<PlaceResponse>>) {
        remoteDataSource.getSearchByKeyword(keyword, callback)
    }

    override fun getPlaceDetail(
        placeNumber: Int,
        memberNumber: Int?,
        callback: PlaceCallback<PlaceResponse>
    ) {
        remoteDataSource.getPlaceDetail(placeNumber, memberNumber, callback)
    }

    override fun getMyPlaceList(memberNumber: Int, callback: PlaceCallback<List<PlaceResponse>>) {
        remoteDataSource.getMyPlaceList(memberNumber, callback)
    }

    override fun getKeyword(callback: PlaceCallback<List<KeywordResponse>>) {
        remoteDataSource.getKeyword(callback)
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
        imageNumbers: List<Int>,
        callback: PlaceCallback<Boolean>
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
            imageNumbers,
            callback
        )
    }

    override fun updateKeyword(
        placeNumber: Int,
        keywordNames: List<Int>,
        placeKeywordNumbers: List<Int>,
        callback: PlaceCallback<Boolean>
    ) {
        remoteDataSource.updateKeyword(placeNumber, keywordNames, placeKeywordNumbers, callback)
    }

    override fun updateOpeningHours(
        placeNumber: Int,
        openingTimes: List<String>,
        callback: PlaceCallback<Boolean>
    ) {
        remoteDataSource.updateOpeningHours(placeNumber, openingTimes, callback)
    }

    override fun deletePlace(
        placeNumber: Int,
        memberNumber: Int,
        callback: PlaceCallback<Boolean>
    ) {
        remoteDataSource.deletePlace(placeNumber, memberNumber, callback)
    }

    override fun getHomePlaceList(
        memberNumber: Int?,
        callback: PlaceCallback<List<PlaceResponse>>
    ) {
        remoteDataSource.getHomePlaceList(memberNumber, callback)
    }

    override fun checkPlace(
        name: String,
        latitude: String,
        longitude: String,
        callback: PlaceCallback<PlaceDuplicateResponse>
    ) {
        remoteDataSource.checkPlace(name, latitude, longitude, callback)
    }

    companion object {
        fun getInstance(remoteDataSource: PlaceRemoteDataSource): PlaceRepository =
            PlaceRepositoryImpl(remoteDataSource)
    }
}