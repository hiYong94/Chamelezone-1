package tk.yeonaeyong.shopinshop.data.model

import java.io.Serializable

data class Like @JvmOverloads constructor(
    val placeName: String = "",
    val placeKeyword: String = "",
    val placeAddress: String = ""
) : Serializable