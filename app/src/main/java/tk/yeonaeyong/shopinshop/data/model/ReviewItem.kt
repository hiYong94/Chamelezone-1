package tk.yeonaeyong.shopinshop.data.model

import java.io.Serializable

data class ReviewItem(
    val reviewNumber: Int,
    val placeNumber: Int,
    val memberNumber:Int,
    val name: String?,
    val nickName: String?,
    val regiDate: String,
    val content: String,
    val image: String,
    val images: ArrayList<String>,
    val imageNumber: ArrayList<Int>
) : Serializable