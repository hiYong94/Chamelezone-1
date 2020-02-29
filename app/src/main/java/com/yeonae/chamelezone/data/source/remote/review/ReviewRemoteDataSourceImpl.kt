package com.yeonae.chamelezone.data.source.remote.review

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.data.Network
import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.network.api.RetrofitConnection.reviewService
import com.yeonae.chamelezone.network.api.ReviewApi
import com.yeonae.chamelezone.network.model.ReviewResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ReviewRemoteDataSourceImpl(private val reviewApi: ReviewApi) : ReviewRemoteDataSource {

    override fun createReview(
        memberNumber: Int,
        placeNumber: Int,
        content: String,
        images: List<String>,
        callBack: ReviewCallBack<String>
    ) {
        val memberNumber =
            RequestBody.create(MediaType.parse("text/plain"), memberNumber.toString())

        val content = RequestBody.create(MediaType.parse("text/plain"), content)

        val file = ArrayList<MultipartBody.Part>()

        for (i in images.indices) {
            val extends = images[i].split(".").lastOrNull() ?: "*"
            file.add(
                MultipartBody.Part.createFormData(
                    "images",
                    images[i],
                    RequestBody.create(MediaType.parse("image/$extends"), File(images[i]))
                )
            )
        }

        Log.d("create tag placeNum", placeNumber.toString())
        Log.d("create tag memberNum", memberNumber.toString())
        Log.d("create tag content", content.toString())
        Log.d("create tag file", file.toString())

        reviewService.reviewCreate(memberNumber, content, file, placeNumber)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == Network.SUCCESS)
                        response.body().let { callBack.onSuccess("리뷰 등록 성공") }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("create tag error", t.toString())
                    Log.d("create tag error", (call.request().toString()))
                }
            })
    }

    override fun getReviewList(placeNumber: Int, callBack: ReviewCallBack<List<ReviewResponse>>) {
        reviewService.getReviewList(placeNumber).enqueue(object : Callback<List<ReviewResponse>> {
            override fun onFailure(call: Call<List<ReviewResponse>>, t: Throwable) {
                Log.d("review tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<ReviewResponse>>,
                response: Response<List<ReviewResponse>>
            ) {
                if (response.code() == Network.SUCCESS) {
                    response.body()?.let { callBack.onSuccess(it) }
                    Log.d("PlaceReviewList", "장소 리뷰 리스트 성공")
                }
            }
        })
    }

    override fun getMyReviewList(
        memberNumber: Int,
        callBack: ReviewCallBack<List<ReviewResponse>>
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
                        response.body()?.let { callBack.onSuccess(it) }
                        Log.d("HomePlaceList", "나의 리뷰 리스트 성공")
                    }
                }
            })
    }

    override fun updateReview() {

    }

    override fun deleteReview(
        placeNumber: Int,
        reviewNumber: Int,
        memberNumber: Int,
        callBack: ReviewCallBack<String>
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
                        callBack.onSuccess("리뷰 삭제 성공")
                    }
                }
            })
    }

    override fun getReviewDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callBack: ReviewCallBack<ReviewResponse>
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
                        response.body()?.let { callBack.onSuccess(it) }
                }
            })
    }

    override fun getMyReviewDetail(
        placeNumber: Int,
        reviewNumber: Int,
        callBack: ReviewCallBack<ReviewResponse>
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
                        response.body()?.let { callBack.onSuccess(it) }
                }
            })
    }

    companion object {
        const val MEMBER_NUMBER = "memberNumber"

        fun getInstance(reviewApi: ReviewApi): ReviewRemoteDataSource =
            ReviewRemoteDataSourceImpl(reviewApi)
    }
}