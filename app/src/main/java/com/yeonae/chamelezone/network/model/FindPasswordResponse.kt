package com.yeonae.chamelezone.network.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class FindPasswordResponse(
    @SerializedName("emailSendResult")
    val emailCheck: EmailSendResultResponse
)