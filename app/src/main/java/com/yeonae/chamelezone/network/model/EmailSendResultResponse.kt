package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class EmailSendResultResponse(
    @SerializedName("memberNumber")
    val memberNumber: Int
)