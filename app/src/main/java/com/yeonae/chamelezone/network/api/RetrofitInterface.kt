package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface RetrofitInterface {
    @POST("/user")
    fun userRegister(
        @Body user: JsonObject
    ): Call<MemberResponse>

    @POST("/place")
    fun placeRegister(
        @Body place: JsonObject
    ): Call<PlaceResponse>
}