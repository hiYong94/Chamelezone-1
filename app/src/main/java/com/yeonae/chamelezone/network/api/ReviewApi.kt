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
        @Part("memberNumber") memberNumber: RequestBody,
        @Part("content") content: RequestBody,
        @Part file: ArrayList<MultipartBody.Part>,
        @Path ("placeNumber") placeNumber: Int
    ): Call<ResponseBody>

    @GET("/place/{placeNumber}/review")
    fun getReviewList(
        @Path("placeNumber") placeNumber: Int
    ): Call<List<ReviewResponse>>

    @GET("/user/{memberNumber}/review")
    fun getMyReviewList(
        @Path("memberNumber") memberNumber: Int
    ): Call<List<ReviewResponse>>
}