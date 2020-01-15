package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.PlaceResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PlaceApi {
    @POST("/place")
    fun placeRegister(
        @Body place: JsonObject
    ): Call<ResponseBody>

    @GET("/map/search")
    fun mapSearch(
        @Query("name") placeName:String
    ): Call<List<PlaceResponse>>
}