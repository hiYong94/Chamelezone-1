package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
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
        @Path("placeNumber") placeNumber: Int
    ): Call<ResponseBody>

    @Multipart
    @PUT("/place/{placeNumber}/review/{reviewNumber}")
    fun updateReview(
        @Path("placeNumber") placeNumber: Int,
        @Part file: ArrayList<MultipartBody.Part>,
        @Path("reviewNumber") reviewNumber: Int,
        @Part("memberNumber") memberNumber: RequestBody,
        @Part("content") content: RequestBody,
        @Part("imageNumber") imageNumber: ArrayList<RequestBody>
    ): Call<ResponseBody>

    @HTTP(method = "DELETE", path = "/place/{placeNumber}/review/{reviewNumber}", hasBody = true)
    fun deleteReview(
        @Path("placeNumber") placeNumber: Int,
        @Path("reviewNumber") reviewNumber: Int,
        @Body memberNumber: JsonObject
    ): Call<ResponseBody>

    @GET("/place/{placeNumber}/review")
    fun getReviewList(
        @Path("placeNumber") placeNumber: Int
    ): Call<List<ReviewResponse>>

    @GET("/user/{memberNumber}/review")
    fun getMyReviewList(
        @Path("memberNumber") memberNumber: Int
    ): Call<List<ReviewResponse>>

    @GET("/place/{placeNumber}/review/{reviewNumber}")
    fun getReviewDetail(
        @Path("placeNumber") placeNumber: Int,
        @Path("reviewNumber") reviewNumber: Int
    ): Call<ReviewResponse>

    @GET("/place/{placeNumber}/review/{reviewNumber}")
    fun getMyReviewDetail(
        @Path("placeNumber") placeNumber: Int,
        @Path("reviewNumber") reviewNumber: Int
    ): Call<ReviewResponse>

    @GET("/place/{placeNumber}/review/{reviewNumber}")
    fun getMyReviewImageDetail(
        @Path("placeNumber") placeNumber: Int,
        @Path("reviewNumber") reviewNumber: Int
    ): Call<ReviewResponse>
}