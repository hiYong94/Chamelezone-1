package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @SerializedName("like_status")
    val likeStatus: Boolean
)