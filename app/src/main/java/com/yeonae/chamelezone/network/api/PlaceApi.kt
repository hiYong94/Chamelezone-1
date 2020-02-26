package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.PlaceResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PlaceApi {
    @Multipart
    @POST("/place")
    fun placeRegister(
        @Part image: ArrayList<MultipartBody.Part>,
        @Part("memberNumber") memberNumber: RequestBody,
        @Part("keywordName") keywordName: ArrayList<RequestBody>,
        @Part("name") name: RequestBody,
        @Part("address") address: RequestBody,
        @Part("openingTime") openingTime: ArrayList<RequestBody>,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("content") content: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody
    ): Call<ResponseBody>

    @GET("/map/place/{name}")
    fun getPlaceListByMap(
        @Path("name") placeName: String
    ): Call<List<PlaceResponse>>

    @GET("/search/place/{name}")
    fun getPlaceListByName(
        @Path("name") placeName: String
    ): Call<List<PlaceResponse>>

    @GET("/search/area/{name}")
    fun getPlaceListByAddress(
        @Path("name") address: String
    ): Call<List<PlaceResponse>>

    @GET("/search/keyword/{name}")
    fun getPlaceListByKeyword(
        @Path("name") keyword: String
    ): Call<List<PlaceResponse>>

    @GET("/place/{placeNumber}")
    fun getPlaceDetail(
        @Path("placeNumber") placeNumber: Int,
        @Query("memberNumber") memberNumber: Int?
    ): Call<PlaceResponse>

    @GET("/user/{MemberNumber}/place")
    fun getMyPlaceList(
        @Path("MemberNumber") memberNumber: Int
    ): Call<List<PlaceResponse>>

    @PUT("/place/{placeNumber}")
    fun updatePlace(
        @Path("placeNumber") placeNumber: Int,
        @Body place: JsonObject
    ): Call<PlaceResponse>

    @DELETE("/place/{placeNumber}")
    fun deletePlace(
        @Path("placeNumber") placeNumber: Int,
        @Body user: JsonObject
    ): Call<ResponseBody>

    @GET("/place")
    fun getHomePlaceList(): Call<List<PlaceResponse>>

    @GET("/place/{placeNumber}/review")
    fun getPlaceDetailReview(
        @Path("placeNumber") placeNumber: Int
    ): Call<PlaceResponse>
}