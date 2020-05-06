package tk.yeonaeyong.shopinshop.network.model

import com.google.gson.annotations.SerializedName

data class KeywordResponse(
    @SerializedName("keywordNumber")
    val keywordNumber: Int,
    @SerializedName("name")
    val keywordName: String
)