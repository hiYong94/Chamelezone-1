package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class LikeItem(
    val likeStatus: Int?,
    val placeNumber: Int,
    val name: String,
    val keyword: String,
    val address: String,
    val image: String
) : Serializable