package com.yeonae.chamelezone.network.model

import java.io.Serializable

data class Course @JvmOverloads constructor(
    var courseName: String = "",
    var registerDate: String = "",
    var userId: String = "",
    var courseText : String = ""
) : Serializable