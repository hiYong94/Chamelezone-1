package tk.yeonaeyong.shopinshop.data.source.remote.like

import android.util.Log
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tk.yeonaeyong.shopinshop.App
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.Network.REQUEST_ERR
import tk.yeonaeyong.shopinshop.data.Network.SUCCESS
import tk.yeonaeyong.shopinshop.data.repository.like.LikeCallback
import tk.yeonaeyong.shopinshop.network.api.LikeApi
import tk.yeonaeyong.shopinshop.network.api.RetrofitConnection.likeService
import tk.yeonaeyong.shopinshop.network.model.LikeResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse

class LikeRemoteDataSourceImpl private constructor(private val likeApi: LikeApi) :
    LikeRemoteDataSource {
    override fun selectLike(
        memberNumber: Int,
        placeNumber: Int,
        callback: LikeCallback<LikeResponse>
    ) {
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
        likeService.deleteLike(memberNumber, jsonObject).enqueue(object : Callback<LikeResponse> {
            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                }
            }

        })
    }

    override fun getMyLikeList(memberNumber: Int, callback: LikeCallback<List<PlaceResponse>>) {
        likeService.getMyLikeList(memberNumber).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                } else if (response.code() == REQUEST_ERR) {
                    callback.onFailure(
                        App.instance.context()
                            .getString(
                                R.string.click_heart
                            )
                    )
                }
            }

        })
    }

    companion object {
        fun getInstance(likeApi: LikeApi): LikeRemoteDataSource =
            LikeRemoteDataSourceImpl(
                likeApi
            )
    }
}