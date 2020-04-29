package com.yeonae.chamelezone.data.source.remote.review

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.data.Network
import com.yeonae.chamelezone.data.repository.review.ReviewCallback
import com.yeonae.chamelezone.network.api.RetrofitConnection.reviewService
import com.yeonae.chamelezone.network.api.ReviewApi
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.util.Logger
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.URLEncoder

class ReviewRemoteDataSourceImpl(private val reviewApi: ReviewApi) : ReviewRemoteDataSource {

    override fun createReview(
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        images: List<String>,
        callback: ReviewCallback<String>
    ) {
        val memberNumber =
            RequestBody.create(MediaType.parse("text/plain"), memberNumber.toString())

        val content = RequestBody.create(MediaType.parse("text/plain"), content)

        val file = ArrayList<MultipartBody.Part>()
        images.forEachIndexed { index, _ ->
            val imageFile = File(images[index])
            val extends = images[index].split(".").lastOrNull() ?: "*"
            file.add(
                MultipartBody.Part.createFormData(
                    "images",
                    URLEncoder.encode(imageFile.name, "UTF-8"),
                    RequestBody.create(MediaType.parse("image/$extends"), imageFile)
                )
            )
        }

        reviewService.reviewCreate(memberNumber, content, file, placeNumber)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == Network.SUCCESS)
                        response.body().let { callback.onSuccess("리뷰 등록 성공") }
                    else {
                        Logger.d("create error ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("create tag error", t.toString())
                    Log.d("create tag error", (call.request().toString()))
                }
            })
    }

    override fun updateReview(
        images: List<String>,
        reviewNumber: Int,
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        deleteImageNumber: List<Int>,
        callback: ReviewCallback<Boolean>
    ) {
        val memberNumber =
            RequestBody.create(MediaType.parse("text/plain"), memberNumber.toString())

        val content = RequestBody.create(MediaType.parse("text/plain"), content)

        val deleteImageNumberList = ArrayList<RequestBody>()
        deleteImageNumber.forEach {
            deleteImageNumberList.add(
                RequestBody.create(
                    MediaType.parse("text/plain"), it.toString()
                )
            )
        }

        val file = ArrayList<MultipartBody.Part>()
        images.forEach {
            val extends = it.split(".").lastOrNull() ?: "*"
            file.add(
                MultipartBody.Part.createFormData(
                    "images",
                    it,
                    RequestBody.create(MediaType.parse("image/$extends"), File(it))
                )
            )
        }

        reviewService.updateReview(
            placeNumber,
            file,
            reviewNumber,
            memberNumber,
            content,
            deleteImageNumberList
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("create tag error", t.toString())
                Log.d("create tag error", (call.request().toString()))
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == Network.SUCCESS) {
                    response.body().let { callback.onSuccess(true) }
                    Logger.d("리뷰 수정 성공")
                } else {
                    Logger.d("createErrorCode ${response.code()}")
                }
            }
        })
    }

    override fun getReviewList(placeNumber: Int, callback: ReviewCallback<List<ReviewResponse>>) {
        reviewService.getReviewList(placeNumber).enqueue(object : Callback<List<ReviewResponse>> {
            override fun onFailure(call: Call<List<ReviewResponse>>, t: Throwable) {
                Log.d("review tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<ReviewResponse>>,
                response: Response<List<ReviewResponse>>
            ) {
                if (response.code() == Network.SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                    Log.d("PlaceReviewList", "장소 리뷰 리스트 성공")
                }
            }
        })
    }

    override fun getMyReviewList(
        memberNumber: Int,
        callback: ReviewCallback<List<ReviewResponse>>
    ) {
        reviewService.getMyReviewList(memberNumber)
            .enqueue(object : Callback<List<ReviewResponse>> {
                override fun onFailure(call: Call<List<ReviewResponse>>, t: Throwable) {
                    Log.d("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<List<ReviewResponse>>,
                    response: Response<List<ReviewResponse>>
                ) {
                    if (response.code() == Network.SUCCESS) {
                        response.body()?.let { callback.onSuccess(it) }
                        Log.d("HomePlaceList", "나의 리뷰 리스트 성공")
                    }
                }
            })
    }

    override fun deleteReview(
        placeNumber: Int,
        reviewNumber: Int,
        memberNumber: Int,
        callback: ReviewCallback<String>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty(MEMBER_NUMBER, memberNumber)
        }
        reviewService.deleteReview(placeNumber, reviewNumber, jsonObject)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == Network.SUCCESS) {
                        callback.onSuccess("리뷰 삭제 성공")
                    }
                }
            })
    }

    override fun getReviewDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callback: ReviewCallback<ReviewResponse>
    ) {
        reviewService.getReviewDetail(placeNumber, reviewNumber)
            .enqueue(object : Callback<ReviewResponse> {
                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    Log.d("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.code() == Network.SUCCESS)
                        response.body()?.let { callback.onSuccess(it) }
                }
            })
    }

    override fun getMyReviewDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callback: ReviewCallback<ReviewResponse>
    ) {
        reviewService.getMyReviewDetail(placeNumber, reviewNumber)
            .enqueue(object : Callback<ReviewResponse> {
                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    Log.d("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.code() == Network.SUCCESS)
                        response.body()?.let { callback.onSuccess(it) }
                }
            })
    }

    override fun getMyReviewImageDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callback: ReviewCallback<ReviewResponse>
    ) {
        reviewService.getMyReviewImageDetail(placeNumber, reviewNumber)
            .enqueue(object : Callback<ReviewResponse> {
                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    Log.d("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.code() == Network.SUCCESS)
                        response.body()?.let { callback.onSuccess(it) }
                }
            })
    }

    companion object {
        const val MEMBER_NUMBER = "memberNumber"

        fun getInstance(reviewApi: ReviewApi): ReviewRemoteDataSource =
            ReviewRemoteDataSourceImpl(reviewApi)
    }
}