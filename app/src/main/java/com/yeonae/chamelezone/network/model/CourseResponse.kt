package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("courseNumber")
    val courseNumber: Int,
    @SerializedName("placeNumber")
    val placeNumber: Int,
    @SerializedName("memberNumber")
    val memberNumber: Int,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("regiDate")
    val regiDate: String,
    @SerializedName("fileName")
    val fileName: String,
    @SerializedName("fileExtension")
    val fileExtension: String
)