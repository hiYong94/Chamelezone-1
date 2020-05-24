package tk.yeonaeyong.shopinshop.data.model

import java.io.Serializable

data class LikeItem(
    val placeNumber: Int,
    val memberNumber: Int,
    val name: String,
    val keyword: String,
    val address: String,
    val image: String
) : Serializable