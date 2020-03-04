package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class ReviewItem(
    val reviewNumber: Int,
    val placeNumber: Int,
    val name: String,
    val content: String,
    val images: String
) : Serializable