package tk.yeonaeyong.shopinshop.network.model

import com.google.gson.annotations.SerializedName

data class PlaceDuplicateResponse(
    @SerializedName("place_check")
    val placeCheck: String
)