package tk.yeonaeyong.shopinshop.network.model

import com.google.gson.annotations.SerializedName


data class SecurityCodeResponse(
    @SerializedName("matchResult")
    val matchResult: Boolean
)