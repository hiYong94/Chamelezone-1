package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class MemberResponse(
    @SerializedName("placeNumber") val placeNumber: Int,
    @SerializedName("keywordNumber") val keywordNumber: Int,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("openingTime") val openingTime: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("content") val content: String,
    @SerializedName("fileName") val fileName: String,
    @SerializedName("fileExtension") val fileExtension: String,
    @SerializedName("regiDate") val regiDate: String
)