package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName
import com.yeonae.chamelezone.data.model.LikeStatusItem

data class LikeResponse(
    @SerializedName("likeStatus")
    val likeStatus: Boolean
) {
    fun toLikeStatusItem(): LikeStatusItem {
        return LikeStatusItem(
            likeStatus
        )
    }
}