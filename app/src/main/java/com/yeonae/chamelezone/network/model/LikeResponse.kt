package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @SerializedName("likeStatus")
    val likeStatus: Boolean
)