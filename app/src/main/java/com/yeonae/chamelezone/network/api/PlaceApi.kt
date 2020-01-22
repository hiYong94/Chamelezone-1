package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.PlaceResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PlaceApi {
    @POST("/place")
    fun placeRegister(
        @Body place: JsonObject
    ): Call<ResponseBody>

    @GET("/search/{name}")
    fun getPlaceListByMap(
        @Path("name") placeName: String
    ): Call<List<PlaceResponse>>

    @GET("/search/{name}")
    fun getPlaceListByName(
        @Path("name") placeName: String
    ): Call<List<PlaceResponse>>

    @GET("/search/{address}")
    fun getPlaceListByAddress(
        @Path("address") address: String
    ): Call<List<PlaceResponse>>

    @GET("/search/{keywordName}")
    fun getPlaceListByKeyword(
        @Path("keywordName") keyword: String
    ): Call<List<PlaceResponse>>

    @GET("/place")
    fun getPlaceDetail(
        @Query("placeNumber") placeNumber: String
    ): Call<PlaceResponse>
}