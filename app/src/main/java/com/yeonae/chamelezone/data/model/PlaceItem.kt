package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class PlaceItem @JvmOverloads constructor(
    val placeNumber: Int,
    val name: String,
    val keyword: String,
    val address: String,
    val image: String
) : Serializable