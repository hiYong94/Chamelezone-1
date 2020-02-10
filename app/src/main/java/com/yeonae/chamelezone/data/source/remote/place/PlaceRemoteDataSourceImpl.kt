package com.yeonae.chamelezone.data.source.remote.place

import android.util.Log
import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.network.api.PlaceApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.keywordService
import com.yeonae.chamelezone.network.api.RetrofitConnection.placeService
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import okhttp3.*
import okio.Buffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.math.BigDecimal

class PlaceRemoteDataSourceImpl private constructor(private val placeApi: PlaceApi) :
    PlaceRemoteDataSource {
    override fun registerPlace(
        keywordName: List<Int>,
        name: String,
        address: String,
        openingTime: List<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        images: String,
        callBack: PlaceCallBack<String>
    ) {
        val map = hashMapOf<String, RequestBody>()
        val name = RequestBody.create(
            MediaType.parse("text/plain"), name
        )
        val keywordName = RequestBody.create(
            MediaType.parse("text/plain"), keywordName.toString()
        )
        val address = RequestBody.create(
            MediaType.parse("text/plain"), address
        )
        val openingTime = RequestBody.create(
            MediaType.parse("text/plain"), openingTime.toString()
        )
        val phoneNumber = RequestBody.create(
            MediaType.parse("text/plain"), phoneNumber
        )
        val content = RequestBody.create(
            MediaType.parse("text/plain"), content
        )
        val latitude = RequestBody.create(
            MediaType.parse("text/plain"), latitude.toString()
        )
        val longitude = RequestBody.create(
            MediaType.parse("text/plain"), longitude.toString()
        )
        val images = RequestBody.create(
            MediaType.parse("image/*"), images
        )

        map["name"] = name
        map["address"] = address
        map["keywordName"] = keywordName
        map["openingTime"] = openingTime
        map["phoneNumber"] = phoneNumber
        map["content"] = content
        map["latitude"] = latitude
        map["longitude"] = longitude
        map["images"] = images

//        val images = MultipartBody.Part.createFormData(
//            "images",
//            images,
//            RequestBody.create(MediaType.parse("image/*"), images)
//        )

        placeService.placeRegister(map).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                    callBack.onSuccess("장소 등록 성공")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                (call.request().body() as MultipartBody).parts().forEach {
                    Log.d("STEP 4", "${bodyToString(call.request().body() as MultipartBody)}")
                }
                Log.d("register call request", call.request().toString())
                Log.e("register tag", t.toString())
            }
        })
    }

    private fun bodyToString(request: RequestBody): String {
        try {
            val buffer = Buffer()
            request.writeTo(buffer)
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }
    }

    override fun getSearchByMap(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        placeService.getPlaceListByMap(placeName).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == 200) {
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
                if (response.code() == 200) {
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
                if (response.code() == 200) {
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
                if (response.code() == 200) {
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
                if (response.code() == 200) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }

        })
    }

    override fun getMyPlaceList(memberNumber: Int, callBack: PlaceCallBack<List<PlaceResponse>>) {
        placeService.getMyPlaceList(memberNumber).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == 200) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
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
                if (response.code() == 200) {
                    response.body()?.let { callBack.onSuccess(it) }
                }
            }

        })
    }

    override fun modifyPlace() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePlace(placeNumber: Int, callBack: PlaceCallBack<String>) {
        placeService.deletePlace(placeNumber).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    callBack.onSuccess("장소 삭제 성공")
                }
            }

        })
    }

    companion object {
        fun getInstance(placeApi: PlaceApi): PlaceRemoteDataSource =
            PlaceRemoteDataSourceImpl(placeApi)
    }
}