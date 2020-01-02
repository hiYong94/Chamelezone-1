package com.yeonae.chamelezone.data.source.remote.place

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.network.api.RetrofitConnection
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceRemoteDataSourceImpl private constructor(private val retrofitConnection: RetrofitConnection) :
    PlaceRemoteDataSource {
    override fun registerPlace(
        keywordNumber: Int,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String,
        callBack: PlaceCallBack
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("keywordNumber", keywordNumber)
            addProperty("name", name)
            addProperty("address", address)
            addProperty("openingTime", openingTime)
            addProperty("phoneNumber", phoneNumber)
            addProperty("content", content)
        }

        retrofitConnection.placeService.placeRegister(jsonObject).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                callBack.onSuccess("장소 등록 성공")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })
    }

    override fun getPlace() {

    }

    override fun deletePlace() {

    }

    override fun modifyPlace() {

    }

    companion object {
        fun getInstance(retrofitConnection: RetrofitConnection): PlaceRemoteDataSource =
            PlaceRemoteDataSourceImpl(retrofitConnection)
    }

}