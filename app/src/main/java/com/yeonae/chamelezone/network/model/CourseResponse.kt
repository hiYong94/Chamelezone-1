package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName
import com.yeonae.chamelezone.data.model.CourseItem
import com.yeonae.chamelezone.data.model.MyCourseItem
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE

data class CourseResponse(
    @SerializedName("courseNumber")
    val courseNumber: Int,
    @SerializedName("placeNumber")
    val placeNumber: Int,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("savedImageName")
    val savedImageName: String,
    @SerializedName("regiDate")
    val regiDate: String,
    @SerializedName("keywordName")
    val keywordName: ArrayList<String>,
    @SerializedName("placeName")
    val placeName: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("placeImage")
    val placeImages: String,
    @SerializedName("courseImage")
    val courseImage: String
) {
    fun toCourseItem(): CourseItem {
        val imageFormat = IMAGE_RESOURCE + savedImageName

        return CourseItem(
            courseNumber,
            nickName,
            title,
            imageFormat,
            regiDate
        )
    }

    fun toMyCourseItem(): MyCourseItem {
        val imageFormat = IMAGE_RESOURCE + savedImageName

        return MyCourseItem(
            courseNumber,
            title,
            content,
            imageFormat
        )
    }
}