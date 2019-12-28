package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.MemberResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberApi {
    @POST("/user")
    fun userRegister(
        @Body user: JsonObject
    ): Call<MemberResponse>
}