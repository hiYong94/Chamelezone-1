package com.yeonae.chamelezone.data.source.remote.course

import android.util.Log
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.repository.course.CourseCallback
import com.yeonae.chamelezone.network.api.CourseApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.courseService
import com.yeonae.chamelezone.network.model.CourseResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.net.URLEncoder


class CourseRemoteDataSourceImpl private constructor(private val courseApi: CourseApi) :
    CourseRemoteDataSource {
    override fun registerCourse(
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        image: String,
        callback: CourseCallback<String>
    ) {

        val file = File(image)
        val extends = image.split(".").lastOrNull() ?: "*"
        val image = MultipartBody.Part.createFormData(
            "image",
            URLEncoder.encode(file.name, "UTF-8"),
            RequestBody.create(MediaType.parse("image/$extends"), file)
        )

        val memberNumber = RequestBody.create(
            MediaType.parse("text/plain"), memberNumber.toString()
        )

        val placeNumber = ArrayList<RequestBody>()
        for (i in placeNumbers.indices) {
            placeNumber.add(
                RequestBody.create(
                    MediaType.parse("text/plain"), placeNumbers[i].toString()
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
                    if (response.code() == SUCCESS) {
                        callback.onSuccess(App.instance.context().getString(R.string.success_register_course))
                    } else {
                        callback.onFailure("코스 등록 실패")
                    }
                    Log.d("STEP 4", "${bodyToString(call.request().body() as MultipartBody)}")
                    (call.request().body() as MultipartBody).parts().forEach {
                        Log.d("STEP 5", "${bodyToString(it.body() as RequestBody)}")
                    }
                    Log.d("courseRegister", response.code().toString())
                }

            })
    }

    override fun getCourseList(callback: CourseCallback<List<CourseResponse>>) {
        courseService.getCourseList().enqueue(object : Callback<List<CourseResponse>> {
            override fun onFailure(call: Call<List<CourseResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<CourseResponse>>,
                response: Response<List<CourseResponse>>
            ) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                }
            }
        })
    }

    override fun getCourseDetail(
        courseNumber: Int,
        callback: CourseCallback<List<CourseResponse>>
    ) {
        courseService.getCourseDetail(courseNumber)
            .enqueue(object : Callback<List<CourseResponse>> {
                override fun onFailure(call: Call<List<CourseResponse>>, t: Throwable) {
                    Log.e("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<List<CourseResponse>>,
                    response: Response<List<CourseResponse>>
                ) {
                    if (response.code() == SUCCESS) {
                        response.body()?.let { callback.onSuccess(it) }
                    }
                }

            })
    }

    override fun getMyCourseList(
        memberNumber: Int,
        callback: CourseCallback<List<CourseResponse>>
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
                    if (response.code() == SUCCESS) {
                        response.body()?.let { callback.onSuccess(it) }
                    } else if (response.code() == REQUEST_ERR) {
                        callback.onFailure(App.instance.context().getString(R.string.register_my_course))
                    }
                }

            })
    }

    override fun modifyCourse(
        courseNumber: Int,
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        image: String,
        imageNumber: Int,
        callback: CourseCallback<Boolean>
    ) {
        val file = File(image)
        val extends = image.split(".").lastOrNull() ?: "*"
        val image = MultipartBody.Part.createFormData(
            "image",
            URLEncoder.encode(file.name, "UTF-8"),
            RequestBody.create(MediaType.parse("image/$extends"), file)
        )

        val imageNumber = RequestBody.create(
            MediaType.parse("text/plain"), imageNumber.toString()
        )

        val memberNumber = RequestBody.create(
            MediaType.parse("text/plain"), memberNumber.toString()
        )

        val placeNumber = ArrayList<RequestBody>()
        for (i in placeNumbers.indices) {
            placeNumber.add(
                RequestBody.create(
                    MediaType.parse("text/plain"), placeNumbers[i].toString()
                )
            )
        }
        val title = RequestBody.create(
            MediaType.parse("text/plain"), title
        )
        val content = RequestBody.create(
            MediaType.parse("text/plain"), content
        )

        courseService.updateCourse(
            courseNumber,
            image,
            imageNumber,
            memberNumber,
            placeNumber,
            title,
            content
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
                Log.d("STEP 4", "${bodyToString(call.request().body() as MultipartBody)}")
                (call.request().body() as MultipartBody).parts().forEach {
                    Log.d("STEP 5", "${bodyToString(it.body() as RequestBody)}")
                }
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("STEP 4", "${bodyToString(call.request().body() as MultipartBody)}")
                (call.request().body() as MultipartBody).parts().forEach {
                    Log.d("STEP 5", "${bodyToString(it.body() as RequestBody)}")
                }
                if (response.code() == SUCCESS) {
                    callback.onSuccess(true)
                }
            }

        })
    }

    override fun modifyCourse(
        courseNumber: Int,
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        savedImageName: String,
        callback: CourseCallback<Boolean>
    ) {

        val memberNumber = RequestBody.create(
            MediaType.parse("text/plain"), memberNumber.toString()
        )

        val placeNumber = ArrayList<RequestBody>()
        for (i in placeNumbers.indices) {
            placeNumber.add(
                RequestBody.create(
                    MediaType.parse("text/plain"), placeNumbers[i].toString()
                )
            )
        }
        val title = RequestBody.create(
            MediaType.parse("text/plain"), title
        )
        val content = RequestBody.create(
            MediaType.parse("text/plain"), content
        )
        val savedImageName = RequestBody.create(
            MediaType.parse("text/plain"), savedImageName
        )

        courseService.updateCourse(
            courseNumber,
            memberNumber,
            placeNumber,
            title,
            content,
            savedImageName
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
                Log.d("STEP 4", "${bodyToString(call.request().body() as MultipartBody)}")
                (call.request().body() as MultipartBody).parts().forEach {
                    Log.d("STEP 5", "${bodyToString(it.body() as RequestBody)}")
                }
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("STEP 4", "${bodyToString(call.request().body() as MultipartBody)}")
                (call.request().body() as MultipartBody).parts().forEach {
                    Log.d("STEP 5", "${bodyToString(it.body() as RequestBody)}")
                }
                if (response.code() == SUCCESS) {
                    callback.onSuccess(true)
                }
            }

        })
    }

    override fun deleteCourse(
        courseNumber: Int,
        memberNumber: Int,
        callback: CourseCallback<Boolean>
    ) {
        courseService.deleteCourse(courseNumber, memberNumber)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == SUCCESS) {
                        callback.onSuccess(true)
                    }
                }

            })
    }

    private fun bodyToString(request: RequestBody): String {
        return try {
            val buffer = Buffer()
            request.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }

    companion object {
        private const val SUCCESS = 200
        private const val REQUEST_ERR = 404
        fun getInstance(courseApi: CourseApi): CourseRemoteDataSource =
            CourseRemoteDataSourceImpl(courseApi)
    }
}