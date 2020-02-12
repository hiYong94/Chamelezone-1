package com.yeonae.chamelezone.network.api

import com.yeonae.chamelezone.network.model.ReviewResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ReviewApi {
    @Multipart
    @POST("/place/{placeNumber}/review")
    fun reviewCreate(
        @Part("review") review: RequestBody,
        @Part file: MultipartBody.Part,
        @Path("placeNumber") placeNumber: Int
    ): Call<ResponseBody>

    @GET("/user/{memberNumber}/review")
    fun getReviewList(
        @Path("memberNumber") memberNumber: Int
    ): Call<ReviewResponse>
}