package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class NicknameResponse(
    @SerializedName("nickName_check")
    val nicknameCheck: String
)