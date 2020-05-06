package tk.yeonaeyong.shopinshop.data.model

import java.io.Serializable

data class MapItem(
    val placeNumber: Int,
    val name: String,
    val keyword: String,
    val address: String,
    val image: String,
    val latitude: String,
    val longitude: String
) : Serializable