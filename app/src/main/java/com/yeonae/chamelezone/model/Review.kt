package com.yeonae.chamelezone.model

import java.io.Serializable

data class Review @JvmOverloads constructor(
    var placeName: String = "",
    var reviewContent: String = ""
) : Serializable