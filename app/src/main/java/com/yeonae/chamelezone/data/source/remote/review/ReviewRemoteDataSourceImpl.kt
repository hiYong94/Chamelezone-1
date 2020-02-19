package com.yeonae.chamelezone.data.source.remote.review

import android.util.Log
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
            file.add(
                MultipartBody.Part.createFormData(
                    "images",
                    images[i],
                    RequestBody.create(MediaType.parse("image/*"), File(images[i]))
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
                    if (response.code() == 200)
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
                if (response.code() == SUCCESS) {
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
                    if (response.code() == SUCCESS) {
                        response.body()?.let { callBack.onSuccess(it) }
                        Log.d("HomePlaceList", "나의 리뷰 리스트 성공")
                    }
                }
            })
    }

    override fun updateReview() {

    }

    override fun deleteReview() {

    }

    companion object {
        private const val SUCCESS = 200

        fun getInstance(reviewApi: ReviewApi): ReviewRemoteDataSource =
            ReviewRemoteDataSourceImpl(reviewApi)
    }
}