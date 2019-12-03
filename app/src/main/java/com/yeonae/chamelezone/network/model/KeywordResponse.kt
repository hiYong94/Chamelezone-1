package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class KeywordResponse(
    @SerializedName("keywordNumber") val keywordNumber: Int
)