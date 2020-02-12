package com.yeonae.chamelezone.data.source.remote.course

import android.util.Log
import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.network.api.CourseApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.courseService
import com.yeonae.chamelezone.network.model.CourseResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CourseRemoteDataSourceImpl private constructor(private val courseApi: CourseApi) :
    CourseRemoteDataSource {
    override fun registerCourse(
        memberNumber: Int,
        placeNumber: Int,
        title: String,
        content: String,
        image: String,
        callBack: CourseCallBack<String>
    ) {
        val image =
            MultipartBody.Part.createFormData(
                "images",
                image,
                RequestBody.create(MediaType.parse("image/*"), File(image))
            )

        val memberNumber = RequestBody.create(
            MediaType.parse("text/plain"), memberNumber.toString()
        )
        var placeNumber = ArrayList<RequestBody>()
        for (i in placeNumber.indices) {
            placeNumber.add(
                RequestBody.create(
                    MediaType.parse("text/plain"), placeNumber[i].toString()
                )
            )
        }
        val title = RequestBody.create(
            MediaType.parse("text/plain"), title
        )
        val content = RequestBody.create(
            MediaType.parse("text/plain"), content
        )

        courseService.courseRegister(image, memberNumber, placeNumber, title, content)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
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
                if (response.code() == 200) {
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
                if (response.code() == 200) {
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
                    if (response.code() == 200) {
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
                if (response.code() == 200) {
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