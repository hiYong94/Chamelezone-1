package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName
import com.yeonae.chamelezone.data.model.LikeItem
import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE

data class PlaceResponse(
    @SerializedName("placeNumber")
    val placeNumber: Int,
    @SerializedName("keywordName")
    val keywordName: ArrayList<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("openingTime")
    val openingTime: ArrayList<String>,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("savedImageName")
    val savedImageName: ArrayList<String>,
    @SerializedName("regiDate")
    val regiDate: String,
    @SerializedName("memberNumber")
    val memberNumber: Int?,
    @SerializedName("likeStatus")
    val likeStatus: Boolean
) {
    fun toPlaceItem(response: PlaceResponse): PlaceItem {
        var keywordFormat = ""
        response.keywordName.forEach {
            keywordFormat = if (it == response.keywordName[0]) {
                it
            } else {
                "${keywordFormat}${","} $it"
            }
        }
        val imageFormat = IMAGE_RESOURCE + response.savedImageName[0]

        return PlaceItem(
            response.placeNumber,
            response.name,
            keywordFormat,
            response.address,
            imageFormat
        )
    }

    fun toLikeItem(response: PlaceResponse): LikeItem {
        val keyword = response.keywordName.toString().replace(",", ", ")
        val images = arrayListOf<String>()
        for (i in response.savedImageName.indices) {
            images.add(IMAGE_RESOURCE + response.savedImageName[i])
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
}