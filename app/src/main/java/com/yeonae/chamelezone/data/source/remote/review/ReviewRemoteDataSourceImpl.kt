package com.yeonae.chamelezone.data.source.remote.review

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.network.api.RetrofitConnection.reviewService
import com.yeonae.chamelezone.network.api.ReviewApi
import com.yeonae.chamelezone.network.model.ReviewResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ReviewRemoteDataSourceImpl(private val reviewApi: ReviewApi) : ReviewRemoteDataSource {

    override fun createReview(
        placeName: String,
        nickname: String,
        reviewImg: String,
        content: String,
        callBack: ReviewCallBack<String>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("content", content)
            addProperty("images", reviewImg)
        }

        val file = File(reviewImg)
        val imageReq = RequestBody.create(
            MediaType.parse("image/*"),
            file
        )

        reviewService.reviewCreate(jsonObject, imageReq).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200)
                    response.body().let { callBack.onSuccess("리뷰 등록 성공") }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("tag", t.toString())
            }
        })
    }

    override fun getReviewList(reviewNum: Int, callBack: ReviewCallBack<List<ReviewResponse>>) {
        reviewService.getReviewList(reviewNum).enqueue(object : Callback<ReviewResponse> {
            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {

            }
        })
    }

    override fun getMyReviewList(userId: String, callBack: ReviewCallBack<List<ReviewResponse>>) {

    }

    override fun updateReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun getInstance(reviewApi: ReviewApi): ReviewRemoteDataSource =
            ReviewRemoteDataSourceImpl(reviewApi)
    }
}