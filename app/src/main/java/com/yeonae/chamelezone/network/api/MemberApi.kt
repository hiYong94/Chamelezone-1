package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

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

    @POST("/user/help-pw-code")
    fun findPassword(
        @Body user: JsonObject
    ): Call<FindPasswordResponse>

    @POST("/user/help-pw-code-check")
    fun checkSecurityCode(
        @Body user: JsonObject
    ): Call<SecurityCodeResponse>

    @PUT("/user/password")
    fun changePassword(
        @Body user: JsonObject
    ): Call<ResponseBody>
}