package com.yeonae.chamelezone.data.source.remote.review

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.data.repository.review.ReviewCallBack
import com.yeonae.chamelezone.network.api.RetrofitConnection.reviewService
import com.yeonae.chamelezone.network.api.ReviewApi
import com.yeonae.chamelezone.network.model.ReviewResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        }

        reviewService.reviewCreate(jsonObject).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callBack.onSuccess("리뷰 등록 성공")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("tag", t.toString())
            }
        })
    }

    override fun getReview() {

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