package com.yeonae.chamelezone.data.source.remote.like

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.repository.like.LikeCallback
import com.yeonae.chamelezone.network.api.LikeApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.likeService
import com.yeonae.chamelezone.network.model.LikeResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeRemoteDataSourceImpl private constructor(private val likeApi: LikeApi) :
    LikeRemoteDataSource {
    override fun selectLike(memberNumber: Int, placeNumber: Int, callback: LikeCallback<LikeResponse>) {
        val jsonObject = JsonObject().apply {
            addProperty("placeNumber", placeNumber)
        }
        likeService.selectLike(memberNumber, jsonObject).enqueue(object : Callback<LikeResponse> {
            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                    Log.d("like", response.body().toString())
                }
            }

        })
    }

    override fun deleteLike(
        memberNumber: Int,
        placeNumber: Int,
        callback: LikeCallback<LikeResponse>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("placeNumber", placeNumber)
        }
        likeService.deleteLike(memberNumber, jsonObject).enqueue(object : Callback<LikeResponse>{
            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                    Log.d("like", response.body().toString())
                }
            }

        })
    }

    override fun getMyLikeList(memberNumber: Int, callback: LikeCallback<List<PlaceResponse>>) {
        likeService.getMyLikeList(memberNumber).enqueue(object :Callback<List<PlaceResponse>>{
            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                }else if (response.code() == REQUEST_ERR) {
                    callback.onFailure(App.instance.context().getString(R.string.click_heart))
                }
            }

        })
    }

    companion object {
        private const val SUCCESS = 200
        private const val REQUEST_ERR = 404
        fun getInstance(likeApi: LikeApi): LikeRemoteDataSource =
            LikeRemoteDataSourceImpl(likeApi)
    }
}