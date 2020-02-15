package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

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
    val regiDate: String
)