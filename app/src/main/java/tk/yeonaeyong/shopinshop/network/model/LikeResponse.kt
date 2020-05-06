package tk.yeonaeyong.shopinshop.network.model

import com.google.gson.annotations.SerializedName
import tk.yeonaeyong.shopinshop.data.model.LikeStatusItem

data class LikeResponse(
    @SerializedName("likeStatus")
    val likeStatus: Boolean
) {
    fun toLikeStatusItem(): LikeStatusItem {
        return LikeStatusItem(
            likeStatus
        )
    }
}