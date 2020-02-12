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

    @GET("/map/place/{name}")
    fun getPlaceListByMap(
        @Path("name") placeName: String
    ): Call<List<PlaceResponse>>

    @GET("/search/place/{name}")
    fun getPlaceListByName(
        @Path("name") placeName: String
    ): Call<List<PlaceResponse>>

    @GET("/search/area/{name}")
    fun getPlaceListByAddress(
        @Path("name") address: String
    ): Call<List<PlaceResponse>>

    @GET("/search/keyword/{name}")
    fun getPlaceListByKeyword(
        @Path("name") keyword: String
    ): Call<List<PlaceResponse>>

    @GET("/place/{placeNumber}")
    fun getPlaceDetail(
        @Path("placeNumber") placeNumber: Int
    ): Call<PlaceResponse>

    @GET("/place/{MemberNumber}")
    fun getMyPlaceList(
        @Path("MemberNumber") memberNumber: Int
    ): Call<List<PlaceResponse>>

    @PUT("/place/{placeNumber}")
    fun updatePlace(
        @Path("placeNumber") placeNumber: Int
    ): Call<PlaceResponse>

    @DELETE("/place/{placeNumber}")
    fun deletePlace(
        @Path("placeNumber") placeNumber: Int
    ): Call<ResponseBody>

    @GET("/place")
    fun getHomePlaceList(): Call<List<PlaceResponse>>
}