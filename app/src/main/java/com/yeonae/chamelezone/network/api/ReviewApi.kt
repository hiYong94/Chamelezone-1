package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.ReviewResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ReviewApi {
    @Multipart
    @POST("/review")
    fun reviewCreate(
        @Body review: JsonObject,
        @Part("file\" filename=\"pp.png\" ") file: RequestBody
    ): Call<ResponseBody>

    @GET("/review")
    fun getReviewList(
        @Path("reviewNumber") reviewNumber: Int
    ): Call<ReviewResponse>
}