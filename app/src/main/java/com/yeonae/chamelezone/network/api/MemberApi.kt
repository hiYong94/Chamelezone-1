package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.EmailResponse
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.model.NicknameResponse
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
        @Path("memberNumber") memberNumber: Int,
        @Body user: JsonObject
    ): Call<ResponseBody>

    @DELETE("/user/{memberNumber}")
    fun deleteMember(
        @Path("memberNumber") memberNumber: Int
    ): Call<ResponseBody>

    @GET("/user/email/{email}")
    fun checkEmail(
        @Path("email") email: String
    ): Call<EmailResponse>

    @GET("/user/nick-name/{nickName}")
    fun checkNickname(
        @Path("nickName") nickname: String
    ): Call<NicknameResponse>

    @POST("/user/help-email")
    fun findEmail(
        @Body user: JsonObject
    ): Call<List<EmailResponse>>

    @POST("/user/help-password")
    fun findPassword(
        @Body user: JsonObject
    ): Call<MemberResponse>
}