package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class Like @JvmOverloads constructor(
    var placeName: String = "",
    var placeKeyword: String = "",
    var placeAddress: String = ""
) : Serializable