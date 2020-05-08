package tk.yeonaeyong.shopinshop.network.model

import com.google.gson.annotations.SerializedName

data class VersionResponse(
    @SerializedName("version")
    val version: String
)