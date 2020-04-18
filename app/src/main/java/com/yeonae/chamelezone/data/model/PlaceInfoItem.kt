package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class PlaceInfoItem(
    val placeNumber: Int,
    val name: String,
    val keyword: String,
    val address: String,
    val openingTime: String,
    val phoneNumber: String,
    val content: String,
    val image: String,
    val latitude: String,
    val longitude: String
) : Serializable