package com.yeonae.chamelezone.data.source.remote.course

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.network.api.CourseApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.courseService
import com.yeonae.chamelezone.network.model.CourseResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseRemoteDataSourceImpl private constructor(private val courseApi: CourseApi) :
    CourseRemoteDataSource {
    override fun registerCourse(
        memberNumber: Int,
        placeNumber: Int,
        title: String,
        content: String,
        callBack: CourseCallBack<String>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("memberNumber", memberNumber)
            addProperty("placeNumber", placeNumber)
            addProperty("title", title)
            addProperty("content", content)
        }
        courseService.courseRegister(jsonObject).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    callBack.onSuccess("코스 등록 성공")
                }
            }

        })
    }

    override fun getCourseList(callBack: CourseCallBack<List<CourseResponse>>) {
        courseService.getCourseList().enqueue(object : Callback<List<CourseResponse>> {
            override fun onFailure(call: Call<List<CourseResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<CourseResponse>>,
                response: Response<List<CourseResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
            }
        })
    }

    override fun getCourseDetail(courseNumber: Int, callBack: CourseCallBack<CourseResponse>) {
        courseService.getCourseDetail(courseNumber).enqueue(object : Callback<CourseResponse> {
            override fun onFailure(call: Call<CourseResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<CourseResponse>,
                response: Response<CourseResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
            }

        })
    }

    override fun getMyCourseList(
        memberNumber: Int,
        callBack: CourseCallBack<List<CourseResponse>>
    ) {
        courseService.getMyCourseList(memberNumber)
            .enqueue(object : Callback<List<CourseResponse>> {
                override fun onFailure(call: Call<List<CourseResponse>>, t: Throwable) {
                    Log.e("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<List<CourseResponse>>,
                    response: Response<List<CourseResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { callBack.onSuccess(it) }
                    }
                }

            })
    }

    override fun modifyCourse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCourse(courseNumber: Int, callBack: CourseCallBack<String>) {
        courseService.deleteCourse(courseNumber).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    callBack.onSuccess("코스 삭제 성공")
                }
            }

        })
    }

    companion object {
        fun getInstance(courseApi: CourseApi): CourseRemoteDataSource =
            CourseRemoteDataSourceImpl(courseApi)
    }
}