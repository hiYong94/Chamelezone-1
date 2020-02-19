package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.PlaceResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface LikeApi {
    @POST("/user/{memberNumber}/like")
    fun selectLike(
        @Path("memberNumber") memberNumber: Int,
        @Body placeNumber: JsonObject
    ): Call<ResponseBody>

    @HTTP(method = "DELETE", path = "/user/{memberNumber}/like/{likeNumber}", hasBody = true)
    fun deleteLike(
        @Path("likeNumber") likeNumber: Int,
        @Path("memberNumber") memberNumber: Int,
        @Body placeNumber: JsonObject
    ): Call<ResponseBody>

    @GET("/user/{memberNumber}/likes")
    fun getMyLikeList(
        @Path("memberNumber") memberNumber: Int
    ): Call<List<PlaceResponse>>
}