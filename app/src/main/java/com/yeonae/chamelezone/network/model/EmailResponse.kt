package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class EmailResponse(
    @SerializedName("email_check")
    val emailCheck: String,
    @SerializedName("email")
    val email: String
)