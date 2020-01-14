package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class Review @JvmOverloads constructor(
    var userId: String = "",
    var reviewDate: String = "",
    var reviewImage: String = "",
    var reviewContent: String = "",
    var placeName: String = ""
) : Serializable