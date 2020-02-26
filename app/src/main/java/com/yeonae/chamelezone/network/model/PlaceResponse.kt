package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName
import com.yeonae.chamelezone.data.model.LikeItem
import com.yeonae.chamelezone.data.model.PlaceItem

data class PlaceResponse(
    @SerializedName("placeNumber")
    val placeNumber: Int,
    @SerializedName("keywordName")
    val keywordName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("openingTime")
    val openingTime: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("savedImageName")
    val savedImageName: String,
    @SerializedName("regiDate")
    val regiDate: String,
    @SerializedName("memberNumber")
    val memberNumber: Int,
    @SerializedName("like_status")
    val likeStatus: Int? = null
){
    fun toPlaceItem(response: PlaceResponse): PlaceItem {
        val keyword = response.keywordName.replace(",", ", ")
        val placeImages = response.savedImageName.split(",")
        val images = arrayListOf<String>()
        for (i in placeImages.indices) {
            images.add(IMAGE_RESOURCE + placeImages[i])
        }
        val imageFormat = images[0]

        return PlaceItem(
            response.placeNumber,
            response.name,
            keyword,
            response.address,
            imageFormat
        )
    }

    fun toLikeItem(response: PlaceResponse): LikeItem {
        val keyword = response.keywordName.replace(",", ", ")
        val placeImages = response.savedImageName.split(",")
        val images = arrayListOf<String>()
        for (i in placeImages.indices) {
            images.add(IMAGE_RESOURCE + placeImages[i])
        }
        val imageFormat = images[0]

        return LikeItem(
            response.likeStatus,
            response.placeNumber,
            response.name,
            keyword,
            response.address,
            imageFormat
        )
    }

    companion object {
        private const val IMAGE_RESOURCE = "http://13.209.136.122:3000/image/"
    }
}