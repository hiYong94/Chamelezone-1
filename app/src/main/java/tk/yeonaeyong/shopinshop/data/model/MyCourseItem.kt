package tk.yeonaeyong.shopinshop.data.model

import java.io.Serializable

data class MyCourseItem(
    val courseNumber: Int,
    val title: String,
    val content: String,
    val savedImageName: String
) : Serializable