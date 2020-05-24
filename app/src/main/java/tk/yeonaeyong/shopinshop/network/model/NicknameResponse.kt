package tk.yeonaeyong.shopinshop.network.model

import com.google.gson.annotations.SerializedName

data class NicknameResponse(
    @SerializedName("nickName_check")
    val nicknameCheck: String
)