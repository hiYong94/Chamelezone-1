package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.MemberResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MemberApi {
    @POST("/user")
    fun userRegister(
        @Body user: JsonObject
    ): Call<ResponseBody>

    @POST("/user/login")
    fun login(
        @Body user: JsonObject
    ): Call<List<MemberResponse>>
}