package com.yeonae.chamelezone.data.source.remote.place

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.Network
import com.yeonae.chamelezone.data.repository.place.PlaceCallback
import com.yeonae.chamelezone.network.api.PlaceApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.keywordService
import com.yeonae.chamelezone.network.api.RetrofitConnection.placeService
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.model.PlaceDuplicateResponse
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.util.Logger
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
import java.net.URLEncoder

class PlaceRemoteDataSourceImpl private constructor(private val placeApi: PlaceApi) :
    PlaceRemoteDataSource {
    override fun registerPlace(
        memberNumber: Int,
        keywordNames: List<Int>,
        name: String,
        address: String,
        openingTimes: List<String>,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        images: List<String>,
        callback: PlaceCallback<String>
    ) {
        val imageList = ArrayList<MultipartBody.Part>()
        for (i in images.indices) {
            val file = File(images[i])
            val extends = images[i].split(".").lastOrNull() ?: "*"
            imageList.add(
                MultipartBody.Part.createFormData(
                    "images",
                    URLEncoder.encode(file.name, "UTF-8"),
                    RequestBody.create(MediaType.parse("image/$extends"), file)
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

        val keywords = ArrayList<RequestBody>()
        for (i in keywordNames.indices) {
            keywords.add(
                RequestBody.create(
                    MediaType.parse("text/plain"), keywordNames[i].toString()
                )
            )
        }

        val memberNumber = RequestBody.create(
            MediaType.parse("text/plain"), memberNumber.toString()
        )

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
            imageList,
            memberNumber,
            keywords,
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
                if (response.code() == Network.SUCCESS) {
                    callback.onSuccess(
                        App.instance.context().getString(R.string.success_register_place)
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })
    }

    override fun getSearchByMap(placeName: String, callback: PlaceCallback<List<PlaceResponse>>) {
        placeService.getPlaceListByMap(placeName).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == Network.SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                } else if (response.code() == REQUEST_ERR) {
                    callback.onFailure("검색하신 장소가 없습니다.")
                }
            }

            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

        })
    }

    override fun getSearchByName(name: String, callback: PlaceCallback<List<PlaceResponse>>) {
        placeService.getPlaceListByName(name).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == Network.SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                } else if (response.code() == REQUEST_ERR) {
                    callback.onFailure(App.instance.context().getString(R.string.no_place_found))
                }
            }

            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

        })
    }

    override fun getSearchByAddress(address: String, callback: PlaceCallback<List<PlaceResponse>>) {
        placeService.getPlaceListByAddress(address).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == Network.SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                } else if (response.code() == REQUEST_ERR) {
                    callback.onFailure(App.instance.context().getString(R.string.no_place_found))
                }
            }

            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

        })
    }

    override fun getSearchByKeyword(keyword: String, callback: PlaceCallback<List<PlaceResponse>>) {
        placeService.getPlaceListByKeyword(keyword).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == Network.SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                } else if (response.code() == REQUEST_ERR) {
                    callback.onFailure(App.instance.context().getString(R.string.no_place_found))
                }
            }

            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })
    }

    override fun getPlaceDetail(
        placeNumber: Int,
        memberNumber: Int?,
        callback: PlaceCallback<PlaceResponse>
    ) {
        placeService.getPlaceDetail(placeNumber, memberNumber)
            .enqueue(object : Callback<PlaceResponse> {
                override fun onResponse(
                    call: Call<PlaceResponse>,
                    response: Response<PlaceResponse>
                ) {
                    if (response.code() == Network.SUCCESS) {
                        response.body()?.let { callback.onSuccess(it) }
                    }
                }

                override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                    Log.e("tag", t.toString())
                }

            })
    }

    override fun getMyPlaceList(memberNumber: Int, callback: PlaceCallback<List<PlaceResponse>>) {
        placeService.getMyPlaceList(memberNumber).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == Network.SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                } else if (response.code() == REQUEST_ERR) {
                    callback.onFailure(App.instance.context().getString(R.string.register_my_place))
                }
            }

        })
    }

    override fun getKeyword(callback: PlaceCallback<List<KeywordResponse>>) {
        keywordService.getKeywordList().enqueue(object : Callback<List<KeywordResponse>> {
            override fun onFailure(call: Call<List<KeywordResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<KeywordResponse>>,
                response: Response<List<KeywordResponse>>
            ) {
                if (response.code() == Network.SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                }
            }

        })
    }

    override fun updatePlace(
        placeNumber: Int,
        images: List<String>,
        memberNumber: Int,
        address: String,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        imageNumbers: List<Int>,
        callback: PlaceCallback<Boolean>
    ) {
        val imageList = ArrayList<MultipartBody.Part>()
        for (i in images.indices) {
            val file = File(images[i])
            val extends = images[i].split(".").lastOrNull() ?: "*"
            imageList.add(
                MultipartBody.Part.createFormData(
                    "images",
                    URLEncoder.encode(file.name, "UTF-8"),
                    RequestBody.create(MediaType.parse("image/$extends"), file)
                )
            )
        }

        val memberNumber = RequestBody.create(
            MediaType.parse("text/plain"), memberNumber.toString()
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

        val imageNumberList = ArrayList<RequestBody>()
        for (i in imageNumbers.indices) {
            imageNumberList.add(
                RequestBody.create(
                    MediaType.parse("text/plain"), imageNumbers[i].toString()
                )
            )
        }

        placeService.updatePlace(
            placeNumber,
            imageList,
            memberNumber,
            address,
            phoneNumber,
            content,
            latitude,
            longitude,
            imageNumberList
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == Network.SUCCESS) {
                    callback.onSuccess(true)
                }
            }

        })
    }

    override fun updateKeyword(
        placeNumber: Int,
        keywordNames: List<Int>,
        placeKeywordNumbers: List<Int>,
        callback: PlaceCallback<Boolean>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("keywordName", keywordNames.toString())
            addProperty("placeKeywordNumbers", placeKeywordNumbers.toString())
        }
        placeService.updateKeyword(placeNumber, jsonObject)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("tag", t.toString())
                    Log.d("step5", call.request().body()?.let { bodyToString(it) })
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("responseCode", response.code().toString())
                    Log.d("step5", call.request().body()?.let { bodyToString(it) })
                    if (response.code() == Network.SUCCESS) {
                        callback.onSuccess(true)
                    }
                    if (response.code() == Network.SUCCESS) {
                        callback.onSuccess(true)
                    }
                }

            })
    }

    override fun updateOpeningHours(
        placeNumber: Int,
        openingTimes: List<String>,
        callback: PlaceCallback<Boolean>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("openingTime", openingTimes.toString())
        }
        placeService.updateOpeningHours(placeNumber, jsonObject)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("responseCode", response.code().toString())
                    Log.d("step5", call.request().body()?.let { bodyToString(it) })
                    if (response.code() == Network.SUCCESS) {
                        callback.onSuccess(true)
                    }
                }

            })
    }

    override fun deletePlace(
        placeNumber: Int,
        memberNumber: Int,
        callback: PlaceCallback<Boolean>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("memberNumber", memberNumber)
        }
        placeService.deletePlace(placeNumber, jsonObject).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == Network.SUCCESS) {
                    callback.onSuccess(true)
                }
            }

        })
    }

    override fun getHomePlaceList(
        memberNumber: Int?,
        callback: PlaceCallback<List<PlaceResponse>>
    ) {
        placeService.getHomePlaceList(memberNumber).enqueue(object : Callback<List<PlaceResponse>> {
            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                Log.d("Home error tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<PlaceResponse>>,
                response: Response<List<PlaceResponse>>
            ) {
                if (response.code() == Network.SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                    Logger.d("HomePlaceList 홈 장소 리스트 성공")
                }
            }
        })
    }

    override fun checkPlace(
        name: String,
        latitude: String,
        longitude: String,
        callback: PlaceCallback<PlaceDuplicateResponse>
    ) {
        placeService.checkPlace(name, latitude, longitude)
            .enqueue(object : Callback<PlaceDuplicateResponse> {
                override fun onFailure(call: Call<PlaceDuplicateResponse>, t: Throwable) {
                    Log.d("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<PlaceDuplicateResponse>,
                    response: Response<PlaceDuplicateResponse>
                ) {
                    if (response.code() == Network.SUCCESS) {
                        response.body()?.let { callback.onSuccess(it) }
                    }
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

    companion object {
        private const val REQUEST_ERR = 404

        fun getInstance(placeApi: PlaceApi): PlaceRemoteDataSource =
            PlaceRemoteDataSourceImpl(placeApi)
    }
}