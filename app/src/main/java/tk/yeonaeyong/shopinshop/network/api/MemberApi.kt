package tk.yeonaeyong.shopinshop.network.api

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import tk.yeonaeyong.shopinshop.network.model.*

interface MemberApi {
    @POST("/v1.0/user")
    fun createMember(
        @Body user: JsonObject
    ): Call<ResponseBody>

    @POST("/v1.0/user/login")
    fun getMember(
        @Body user: JsonObject
    ): Call<MemberResponse>

    @PUT("/v1.0/user/{memberNumber}")
    fun updateMember(
        @Path("memberNumber") memberNumber: Int,
        @Body user: JsonObject
    ): Call<ResponseBody>

    @DELETE("/v1.0/user/{memberNumber}")
    fun deleteMember(
        @Path("memberNumber") memberNumber: Int
    ): Call<ResponseBody>

    @GET("/v1.0/user/email/{email}")
    fun checkEmail(
        @Path("email") email: String
    ): Call<EmailResponse>

    @GET("/v1.0/user/nick-name/{nickName}")
    fun checkNickname(
        @Path("nickName") nickname: String
    ): Call<NicknameResponse>

    @POST("/v1.0/user/help-email")
    fun findEmail(
        @Body user: JsonObject
    ): Call<List<EmailResponse>>

    @POST("/v1.0/user/help-pw-code")
    fun findPassword(
        @Body user: JsonObject
    ): Call<FindPasswordResponse>

    @POST("/v1.0/user/help-pw-code-check")
    fun checkSecurityCode(
        @Body user: JsonObject
    ): Call<SecurityCodeResponse>

    @PUT("/v1.0/user/password")
    fun changePassword(
        @Body user: JsonObject
    ): Call<ResponseBody>
}