package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class CourseDetailItem(
    val nickName: String,
    val title: String,
    val content: String,
    val regiDate: String,
    val keywordName: ArrayList<String>,
    val placeName: String,
    val address: String,
    val placeImages: String,
    val courseImage: String
) : Serializable