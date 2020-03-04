package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName
import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE

data class ReviewResponse(
    @SerializedName("reviewNumber")
    val reviewNumber: Int,
    @SerializedName("memberNumber")
    val memberNumber: Int,
    @SerializedName("placeNumber")
    val placeNumber: Int,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("regiDate")
    val regiDate: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("savedImageName")
    val savedImageName: String
) {
    fun toReviewItem(response: ReviewResponse): ReviewItem {
        val reviewImages = response.savedImageName.split(",")
        val imageList = arrayListOf<String>()
        reviewImages.forEachIndexed { index, _ ->
            imageList.add(IMAGE_RESOURCE + reviewImages[index])
        }
        val image = imageList.first()
        return ReviewItem(
            response.reviewNumber,
            response.placeNumber,
            response.name,
            response.regiDate,
            response.content,
            image,
            imageList.toString()
        )
    }
}