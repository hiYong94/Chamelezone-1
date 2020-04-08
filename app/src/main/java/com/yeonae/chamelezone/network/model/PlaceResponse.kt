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
    val memberNumber: Int,
    @SerializedName("likeStatus")
    val likeStatus: Boolean,
    @SerializedName("placeKeywordNumber")
    val placeKeywordNumber: ArrayList<Int>,
    @SerializedName("imageNumber")
    val imageNumber: ArrayList<Int>
) {
    fun toPlaceItem(): PlaceItem {
        var keywordFormat = ""
        keywordName.forEach {
            keywordFormat = if (it == keywordName[0]) {
                it
            } else {
                "${keywordFormat}${","} $it"
            }
        }
        val imageFormat = IMAGE_RESOURCE + savedImageName[0]

        return PlaceItem(
            placeNumber,
            name,
            keywordFormat,
            address,
            imageFormat
        )
    }

    fun toLikeItem(): LikeItem {
        val keyword = keywordName.toString().replace(",", ", ")
        val images = arrayListOf<String>()
        for (i in savedImageName.indices) {
            images.add(IMAGE_RESOURCE + savedImageName[i])
        }
        val imageFormat = images[0]

        return LikeItem(
            likeStatus,
            placeNumber,
            name,
            keyword,
            address,
            imageFormat
        )
    }
}