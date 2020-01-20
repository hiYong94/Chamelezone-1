package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class Course @JvmOverloads constructor(
    val courseName: String = "",
    val registerDate: String = "",
    val userId: String = "",
    val courseText : String = ""
) : Serializable