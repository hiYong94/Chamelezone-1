package com.yeonae.chamelezone.data.source.remote.place

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.network.api.PlaceApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.placeService
import com.yeonae.chamelezone.network.model.PlaceResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceRemoteDataSourceImpl private constructor(private val placeApi: PlaceApi) :
    PlaceRemoteDataSource {

    override fun registerPlace(
        keywordName: String,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String,
        latitude: Double,
        longitude: Double,
        callBack: PlaceCallBack<String>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("keywordName", keywordName)
            addProperty("name", name)
            addProperty("address", address)
            addProperty("openingTime", openingTime)
            addProperty("phoneNumber", phoneNumber)
            addProperty("content", content)
            addProperty("latitude", latitude)
            addProperty("longitude", longitude)
        }

        placeService.placeRegister(jsonObject).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                callBack.onSuccess("장소 등록 성공")
                Log.d("LatLng", "$latitude/$longitude")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })
    }

    override fun searchPlace(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        placeService.mapSearch(placeName).enqueue(object :Callback<List<PlaceResponse>>{
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                response.body()?.let { callBack.onSuccess(it) }
                Log.d("search", response.body().toString())
            }

            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
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
        fun getInstance(placeApi: PlaceApi): PlaceRemoteDataSource =
            PlaceRemoteDataSourceImpl(placeApi)
    }

}