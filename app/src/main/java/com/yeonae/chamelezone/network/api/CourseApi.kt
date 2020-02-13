package com.yeonae.chamelezone.network.api

import com.yeonae.chamelezone.network.model.CourseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface CourseApi {
    @Multipart
    @POST("/course")
    fun courseRegister(
        @Part image: ArrayList<MultipartBody.Part>,
        @Part("memberNumber") memberNumber: RequestBody,
        @Part("placeNumber") placeNumber: ArrayList<RequestBody>,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody
    ): Call<ResponseBody>

    @GET("/course")
    fun getCourseList(
    ): Call<List<CourseResponse>>

    @GET("/course/{courseNumber}")
    fun getCourseDetail(
        @Path("courseNumber") courseNumber: Int
    ): Call<CourseResponse>

    @GET("/course/{MemberNumber}")
    fun getMyCourseList(
        @Path("MemberNumber") memberNumber: Int
    ): Call<List<CourseResponse>>

    @PUT("/course/{courseNumber}")
    fun updateCourse(
        @Path("courseNumber") courseNumber: Int
    ): Call<CourseResponse>

    @DELETE("/course/{courseNumber}")
    fun deleteCourse(
        @Path("courseNumber") courseNumber: Int
    ): Call<ResponseBody>

}