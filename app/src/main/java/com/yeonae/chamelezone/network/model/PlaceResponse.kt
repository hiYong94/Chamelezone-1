package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("memberNumber") val memberNumber: Int,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("regiDate") val regiDate: String
)