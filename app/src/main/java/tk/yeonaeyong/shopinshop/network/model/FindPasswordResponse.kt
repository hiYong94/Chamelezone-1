package tk.yeonaeyong.shopinshop.network.model

import com.google.gson.annotations.SerializedName

data class FindPasswordResponse(
    @SerializedName("emailSendResult")
    val emailCheck: EmailSendResultResponse
)