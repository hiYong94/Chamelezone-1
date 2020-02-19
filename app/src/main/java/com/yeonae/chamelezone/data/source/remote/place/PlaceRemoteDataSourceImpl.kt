package com.yeonae.chamelezone.data.source.remote.place

import android.util.Log
import com.yeonae.chamelezone.data.repository.place.PlaceCallBack
import com.yeonae.chamelezone.network.api.PlaceApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.keywordService
import com.yeonae.chamelezone.network.api.RetrofitConnection.placeService
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.math.BigDecimal

class PlaceRemoteDataSourceImpl private constructor(private val placeApi: PlaceApi) :
    PlaceRemoteDataSource {
    override fun registerPlace(
        keywordName: List<Int>,
        name: String,
        address: String,
        openingTimes: List<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        images: List<String>,
        callBack: PlaceCallBack<String>
    ) {

        val image = ArrayList<MultipartBody.Part>()
        for (i in images.indices) {
            val extends = images[i].split(".").lastOrNull() ?: "*"
            image.add(
                MultipartBody.Part.createFormData(
                    "images",
                    images[i],
                    RequestBody.create(MediaType.parse("image/$extends"), File(images[i]))
                )
            )
        }

        val openingTime = ArrayList<RequestBody>()
        for (i in openingTimes.indices) {
            openingTime.add(
                RequestBody.create(
                    MediaType.parse("text/plain"), openingTimes[i]
                )
            )
        }

        val keyword = ArrayList<RequestBody>()
        for (i in keywordName.indices) {
            keyword.add(
                RequestBody.create(
                    MediaType.parse("text/plain"), keywordName[i].toString()
                )
            )
        }

        val name = RequestBody.create(
            MediaType.parse("text/plain"), name
        )
        val address = RequestBody.create(
            MediaType.parse("text/plain"), address
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

        placeService.placeRegister(
            image,
            keyword,
            name,
            address,
            openingTime,
            phoneNumber,
            content,
            latitude,
            longitude
        ).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == SUCCESS) {
                    Log.d("STEP 4", "${bodyToString(call.request().body() as MultipartBody)}")
                    (call.request().body() as MultipartBody).parts().forEach {
                        Log.d("STEP 5", "${bodyToString(it.body() as RequestBody)}")
                    }
                    callBack.onSuccess("장소 등록 성공")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                (call.request().body() as MultipartBody).parts().forEach {
                    Log.d("STEP 4", "${bodyToString(call.request().body() as MultipartBody)}")
                }
                Log.d("register call request", call.request().toString())
                Log.e("tag", t.toString())
            }
        })
    }

    private fun bodyToString(request: RequestBody): String {
        return try {
            val buffer = Buffer()
            request.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }

    override fun getSearchByMap(placeName: String, callBack: PlaceCallBack<List<PlaceResponse>>) {
        placeService.getPlaceListByMap(placeName).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
                    callBack.onSuccess("장소 삭제 성공")
                }
            }

        })
    }

    override fun getHomePlaceList(callBack: PlaceCallBack<List<PlaceResponse>>) {
        placeService.getHomePlaceList().enqueue(object : Callback<List<PlaceResponse>> {
            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.d("tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callBack.onSuccess(it) }
                    Log.d("HomePlaceList", "홈 장소 리스트 성공")
                }
            }
        })
    }
//
//    override fun getPlaceDetailReview(placeNumber: Int, callBack: PlaceCallBack<PlaceResponse>) {
//        placeService.getPlaceDetailReview(placeNumber).enqueue(object : Callback<PlaceResponse> {
//            override fun onResponse(
//                call: Call<PlaceResponse>,
//                response: Response<PlaceResponse>
//            ) {
//                if (response.code() == 200) {
//                    response.body()?.let { callBack.onSuccess(it) }
//                }
//            }
//
//            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
//                Log.e("tag", t.toString())
//            }
//
//        })
//    }

    companion object {
        private const val SUCCESS = 200
        fun getInstance(placeApi: PlaceApi): PlaceRemoteDataSource =
            PlaceRemoteDataSourceImpl(placeApi)
    }
}