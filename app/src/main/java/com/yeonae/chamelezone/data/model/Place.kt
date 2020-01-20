package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class Place @JvmOverloads constructor(
    val placeName: String = "",
    val placeKeyword: String = "",
    val placeAddress: String = "",
    val placeDistance: String = "",
    val placeImg: String = ""
) : Serializable