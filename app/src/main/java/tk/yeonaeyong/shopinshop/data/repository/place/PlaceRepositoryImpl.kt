package tk.yeonaeyong.shopinshop.data.repository.place

import tk.yeonaeyong.shopinshop.data.source.remote.place.PlaceRemoteDataSource
import tk.yeonaeyong.shopinshop.network.model.KeywordResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceDuplicateResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import java.math.BigDecimal

class PlaceRepositoryImpl private constructor(private val remoteDataSource: PlaceRemoteDataSource) :
    PlaceRepository {
    override fun registerPlace(
        memberNumber: Int,
        keywordNames: List<Int>,
        name: String,
        address: String,
        addressDetail: String,
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
            addressDetail,
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
        insertImages: List<String>,
        deleteImageNumbers: List<Int>,
        memberNumber: Int,
        address: String,
        addressDetail: String,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        callback: PlaceCallback<Boolean>
    ) {
        remoteDataSource.updatePlace(
            placeNumber,
            insertImages,
            deleteImageNumbers,
            memberNumber,
            address,
            addressDetail,
            phoneNumber,
            content,
            latitude,
            longitude,
            callback
        )
    }

    override fun updatePlace(
        placeNumber: Int,
        memberNumber: Int,
        address: String,
        addressDetail: String,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        callback: PlaceCallback<Boolean>
    ) {
        remoteDataSource.updatePlace(
            placeNumber,
            memberNumber,
            address,
            addressDetail,
            phoneNumber,
            content,
            latitude,
            longitude,
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

    override fun getKeywordRank(callback: PlaceCallback<List<KeywordResponse>>) {
        remoteDataSource.getKeywordRank(callback)
    }

    companion object {
        fun getInstance(remoteDataSource: PlaceRemoteDataSource): PlaceRepository =
            PlaceRepositoryImpl(
                remoteDataSource
            )
    }
}