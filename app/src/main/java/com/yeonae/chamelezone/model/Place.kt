package com.yeonae.chamelezone.model

import java.io.Serializable

data class Place (val placeImg: String, val placeName: String, val distance: String, val keyword: String)
    :Serializable