package com.yeonae.chamelezone.data.source.remote.place

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonObject
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.network.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceRemoteDataSourceImpl private constructor() :
    PlaceRemoteDataSource {
    private val retrofitConnection = RetrofitConnection
    private val context: Context = App.instance.context()
    override fun registerPlace(
        keywordNumber: Int,
        name: String,
        address: String,
        openingTime: String,
        phoneNumber: String,
        content: String
    ) {
        //val realAddress = "${tv_place_address.text}" + " " + "${edt_detail_address.text}"
        val placeResponse = PlaceResponse(
            1,
            keywordNumber,
            name,
            address,
            openingTime,
            phoneNumber,
            content,
            "",
            "",
            ""
        )
        val jsonObject = JsonObject().apply {
            addProperty("keywordNumber", placeResponse.keywordNumber)
            addProperty("name", placeResponse.name)
            addProperty("address", placeResponse.address)
            addProperty("openingTime", placeResponse.openingTime)
            addProperty("phoneNumber", placeResponse.phoneNumber)
            addProperty("content", placeResponse.content)
        }

        retrofitConnection.placeService.placeRegister(jsonObject).enqueue(object :
            Callback<PlaceResponse> {
            override fun onResponse(
                call: Call<PlaceResponse>,
                response: Response<PlaceResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "장소 등록 성공", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
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
        fun getInstance(): PlaceRemoteDataSource = PlaceRemoteDataSourceImpl()
    }

}