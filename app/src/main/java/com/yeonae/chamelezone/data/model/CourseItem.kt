package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class CourseItem(
    val courseNumber: Int,
    val nickName: String,
    val title: String,
    val savedImageName: String,
    val regiDate: String
) : Serializable