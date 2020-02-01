package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReviewApi {
    @POST("/review")
    fun reviewCreate(
        @Body review: JsonObject
    ): Call<ResponseBody>
}