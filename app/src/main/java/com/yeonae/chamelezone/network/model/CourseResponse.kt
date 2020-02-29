package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName
import com.yeonae.chamelezone.data.model.CourseItem
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
    val regiDate: String
) {
    fun toCourseItem(response: CourseResponse): CourseItem {
        val regiDateFormat = response.regiDate.split("T").first()
        val placeImages = response.savedImageName.split(",")
        val images = arrayListOf<String>()
        for (i in placeImages.indices) {
            images.add(IMAGE_RESOURCE + placeImages[i])
        }
        val imageFormat = images[0]

        return CourseItem(
            response.courseNumber,
            response.nickName,
            response.title,
            imageFormat,
            regiDateFormat
        )
    }
}