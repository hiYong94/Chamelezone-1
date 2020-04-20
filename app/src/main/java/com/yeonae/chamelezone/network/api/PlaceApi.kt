package com.yeonae.chamelezone.network.api

import com.google.gson.JsonObject
import com.yeonae.chamelezone.network.model.PlaceDuplicateResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PlaceApi {
    @Multipart
    @POST("/v1.0/place")
    fun placeRegister(
        @Part image: ArrayList<MultipartBody.Part>,
        @Part("memberNumber") memberNumber: RequestBody,
        @Part("keywordName") keywordName: ArrayList<RequestBody>,
        @Part("name") name: RequestBody,
        @Part("address") address: RequestBody,
        @Part("addressDetail") addressDetail: RequestBody,
        @Part("openingTime") openingTime: ArrayList<RequestBody>,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("content") content: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody
    ): Call<ResponseBody>

    @GET("/v1.0/map/place/{name}")
    fun getPlaceListByMap(
        @Path("name") placeName: String
    ): Call<List<PlaceResponse>>

    @GET("/v1.0/search/place/{name}")
    fun getPlaceListByName(
        @Path("name") placeName: String
    ): Call<List<PlaceResponse>>

    @GET("/v1.0/search/area/{name}")
    fun getPlaceListByAddress(
        @Path("name") address: String
    ): Call<List<PlaceResponse>>

    @GET("/v1.0/search/keyword/{name}")
    fun getPlaceListByKeyword(
        @Path("name") keyword: String
    ): Call<List<PlaceResponse>>

    @GET("/v1.0/place/{placeNumber}")
    fun getPlaceDetail(
        @Path("placeNumber") placeNumber: Int,
        @Query("memberNumber") memberNumber: Int?
    ): Call<PlaceResponse>

    @GET("/v1.0/user/{MemberNumber}/place")
    fun getMyPlaceList(
        @Path("MemberNumber") memberNumber: Int
    ): Call<List<PlaceResponse>>

    @Multipart
    @PUT("/v1.0/place/{placeNumber}")
    fun updatePlace(
        @Path("placeNumber") placeNumber: Int,
        @Part images: ArrayList<MultipartBody.Part>,
        @Part("memberNumber") memberNumber: RequestBody,
        @Part("address") address: RequestBody,
        @Part("addressDetail") addressDetail: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("content") content: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("imageNumber") imageNumber: ArrayList<RequestBody>
    ): Call<ResponseBody>

    @PUT("/v1.0/place/{placeNumber}/keyword")
    fun updateKeyword(
        @Path("placeNumber") placeNumber: Int,
        @Body keyword: JsonObject
    ): Call<ResponseBody>

    @PUT("/v1.0/place/{placeNumber}/openingTime")
    fun updateOpeningHours(
        @Path("placeNumber") placeNumber: Int,
        @Body openingTime: JsonObject
    ): Call<ResponseBody>

    @HTTP(method = "DELETE", path = "/v1.0/place/{placeNumber}", hasBody = true)
    fun deletePlace(
        @Path("placeNumber") placeNumber: Int,
        @Body user: JsonObject
    ): Call<ResponseBody>

    @GET("/v1.0/place")
    fun getHomePlaceList(
        @Query("memberNumber") memberNumber: Int?
    ): Call<List<PlaceResponse>>

    @GET("/v1.0/place/duplicate-check")
    fun checkPlace(
        @Query("name") name: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Call<PlaceDuplicateResponse>

}