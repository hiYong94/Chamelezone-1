package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class Place @JvmOverloads constructor(
    var placeName: String = "",
    var placeKeyword: String = "",
    var placeAddress: String = "",
    val placeDistance: String = "",
    val placeImg: String = ""
) : Serializable