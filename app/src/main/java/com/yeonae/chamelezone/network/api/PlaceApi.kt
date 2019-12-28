package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PlaceApi {
    @POST("/place")
    fun placeRegister(
        @Body place: JsonObject
    ): Call<PlaceResponse>
}