package tk.yeonaeyong.shopinshop.network.model

import com.google.gson.annotations.SerializedName

data class EmailSendResultResponse(
    @SerializedName("memberNumber")
    val memberNumber: Int
)