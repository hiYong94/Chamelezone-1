package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @SerializedName("likeNumber")
    val likeNumber: String,
    @SerializedName("placeNumber")
    val placeNumber: String,
    @SerializedName("memberNumber")
    val memberNumber: String
)