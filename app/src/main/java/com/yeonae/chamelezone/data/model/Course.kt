package com.yeonae.chamelezone.data.model

import java.io.Serializable

data class Course @JvmOverloads constructor(
    var courseName: String = "",
    var registerDate: String = "",
    var userId: String = "",
    var courseText : String = "",
    var courseImg : String = ""
) : Serializable