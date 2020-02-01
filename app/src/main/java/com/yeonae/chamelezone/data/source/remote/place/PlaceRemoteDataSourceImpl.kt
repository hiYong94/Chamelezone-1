package com.yeonae.chamelezone.data.source.remote.place

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.network.api.PlaceApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.keywordService
import com.yeonae.chamelezone.network.api.RetrofitConnection.placeService
import com.yeonae.chamelezone.network.model.KeywordResponse
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
        latitude: String,
        longitude: String,
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

    override fun getSearchByMap(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        placeService.getPlaceListByMap(placeName).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

        })
    }

    override fun getSearchByName(name: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        placeService.getPlaceListByName(name).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

        })
    }

    override fun getSearchByAddress(address: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        placeService.getPlaceListByAddress(address).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

        })
    }

    override fun getSearchByKeyword(keyword: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        placeService.getPlaceListByKeyword(keyword).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })
    }

    override fun getPlaceDetail(placeNumber: Int, callBack: PlaceCallBack<PlaceResponse>) {
        placeService.getPlaceDetail(placeNumber).enqueue(object : Callback<PlaceResponse> {
            override fun onResponse(
                call: Call<PlaceResponse>,
                response: Response<PlaceResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                Log.e("yeon_tag", t.toString())
            }

        })
    }

    override fun getKeyword(callBack: PlaceCallBack<List<KeywordResponse>>) {
        keywordService.getKeywordList().enqueue(object : Callback<List<KeywordResponse>> {
            override fun onFailure(call: Call<List<KeywordResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<KeywordResponse>>,
                response: Response<List<KeywordResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
            }

        })
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