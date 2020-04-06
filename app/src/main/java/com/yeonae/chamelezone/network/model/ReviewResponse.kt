package com.yeonae.chamelezone.network.model

import com.google.gson.annotations.SerializedName
import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.util.Logger

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
    val savedImageName: ArrayList<String>
) {
    fun toReviewItem(): ReviewItem? {
        val reviewImages = savedImageName
        if (reviewImages.isNotEmpty()) {
            val image = IMAGE_RESOURCE + savedImageName.first()
            return ReviewItem(
                reviewNumber,
                placeNumber,
                memberNumber,
                name,
                nickName,
                regiDate,
                content,
                image,
                reviewImages
            )
        }
        return null
    }
}