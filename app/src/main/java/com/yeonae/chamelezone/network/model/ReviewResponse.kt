package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("reviewNumber")
    val reviewNumber: Int,
    @SerializedName("memberNumber")
    val memberNumber: Int,
    @SerializedName("placeNumber")
    val placeNumber: Int,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("regiDate")
    val regiDate: String

)