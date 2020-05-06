package tk.yeonaeyong.shopinshop.data.model

import java.io.Serializable

data class Review @JvmOverloads constructor(
    val userId: String = "",
    val reviewDate: String = "",
    val reviewImage: String = "",
    val reviewContent: String = "",
    val placeName: String = ""
) : Serializable