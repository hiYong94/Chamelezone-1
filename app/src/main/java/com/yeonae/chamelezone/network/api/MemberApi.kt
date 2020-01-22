package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.MemberResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberApi {
    @POST("/user")
    fun createMember(
        @Body user: JsonObject
    ): Call<ResponseBody>

    @POST("/user/login")
    fun getMember(
        @Body user: JsonObject
    ): Call<MemberResponse>

    @PUT("/user/{memberNumber}")
    fun updateMember(
        @Path("memberNumber") memberNumber: Int
    ): Call<MemberResponse>

    @DELETE("/user/{memberNumber}")
    fun deleteMember(
        @Path("memberNumber") memberNumber: Int
    ): Call<ResponseBody>

}