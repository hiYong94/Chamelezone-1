package tk.yeonaeyong.shopinshop.network.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import tk.yeonaeyong.shopinshop.network.model.CourseResponse

interface CourseApi {
    @Multipart
    @POST("/v1.0/course")
    fun courseRegister(
        @Part image: MultipartBody.Part,
        @Part("memberNumber") memberNumber: RequestBody,
        @Part("placeNumber") placeNumber: ArrayList<RequestBody>,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody
    ): Call<ResponseBody>

    @GET("/v1.0/course")
    fun getCourseList(
    ): Call<List<CourseResponse>>

    @GET("/v1.0/course/{courseNumber}")
    fun getCourseDetail(
        @Path("courseNumber") courseNumber: Int
    ): Call<List<CourseResponse>>

    @GET("/v1.0/user/{memberNumber}/course")
    fun getMyCourseList(
        @Path("memberNumber") memberNumber: Int
    ): Call<List<CourseResponse>>

    @Multipart
    @PUT("/v1.0/course/{courseNumber}")
    fun updateCourse(
        @Path("courseNumber") courseNumber: Int,
        @Part image: MultipartBody.Part,
        @Part("imageNumber") imageNumber: RequestBody,
        @Part("memberNumber") memberNumber: RequestBody,
        @Part("placeNumber") placeNumber: ArrayList<RequestBody>,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody
    ): Call<ResponseBody>

    @Multipart
    @PUT("/v1.0/course/{courseNumber}")
    fun updateCourse(
        @Path("courseNumber") courseNumber: Int,
        @Part("memberNumber") memberNumber: RequestBody,
        @Part("placeNumber") placeNumber: ArrayList<RequestBody>,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("savedImageName") savedImageName: RequestBody
    ): Call<ResponseBody>

    @DELETE("/v1.0/course/{courseNumber}")
    fun deleteCourse(
        @Path("courseNumber") courseNumber: Int,
        @Query("memberNumber") memberNumber: Int
    ): Call<ResponseBody>

}