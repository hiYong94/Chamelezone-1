package tk.yeonaeyong.shopinshop.data.source.remote.place

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tk.yeonaeyong.shopinshop.App
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.Network.REQUEST_ERR
import tk.yeonaeyong.shopinshop.data.Network.SUCCESS
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceCallback
import tk.yeonaeyong.shopinshop.network.api.PlaceApi
import tk.yeonaeyong.shopinshop.network.api.RetrofitConnection.keywordService
import tk.yeonaeyong.shopinshop.network.api.RetrofitConnection.placeService
import tk.yeonaeyong.shopinshop.network.model.KeywordResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceDuplicateResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import java.io.File
import java.math.BigDecimal
import java.net.URLEncoder

class PlaceRemoteDataSourceImpl private constructor(private val placeApi: PlaceApi) :
    PlaceRemoteDataSource {
    override fun registerPlace(
        memberNumber: Int,
        keywordNames: List<Int>,
        name: String,
        address: String,
        addressDetail: String,
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
        val addressDetail = RequestBody.create(
            MediaType.parse("text/plain"), addressDetail
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
            addressDetail,
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                    if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                }
            }

        })
    }

    override fun updatePlace(
        placeNumber: Int,
        insertImages: List<String>,
        deleteImageNumbers: List<Int>,
        memberNumber: Int,
        address: String,
        addressDetail: String,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        callback: PlaceCallback<Boolean>
    ) {
        val insertImageList = ArrayList<MultipartBody.Part>()
        for (i in insertImages.indices) {
            val file = File(insertImages[i])
            val extends = insertImages[i].split(".").lastOrNull() ?: "*"
            insertImageList.add(
                MultipartBody.Part.createFormData(
                    "images",
                    URLEncoder.encode(file.name, "UTF-8"),
                    RequestBody.create(MediaType.parse("image/$extends"), file)
                )
            )
        }

        val deleteImageNumberList = ArrayList<RequestBody>()
        for (i in deleteImageNumbers.indices) {
            deleteImageNumberList.add(
                RequestBody.create(
                    MediaType.parse("text/plain"), deleteImageNumbers[i].toString()
                )
            )
        }

        val memberNumber = RequestBody.create(
            MediaType.parse("text/plain"), memberNumber.toString()
        )

        val address = RequestBody.create(
            MediaType.parse("text/plain"), address
        )
        val addressDetail = RequestBody.create(
            MediaType.parse("text/plain"), addressDetail
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

        placeService.updatePlace(
            placeNumber,
            insertImageList,
            deleteImageNumberList,
            memberNumber,
            address,
            addressDetail,
            phoneNumber,
            content,
            latitude,
            longitude
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == SUCCESS) {
                    callback.onSuccess(true)
                }
            }

        })
    }

    override fun updatePlace(
        placeNumber: Int,
        memberNumber: Int,
        address: String,
        addressDetail: String,
        phoneNumber: String,
        content: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        callback: PlaceCallback<Boolean>
    ) {
        val memberNumber = RequestBody.create(
            MediaType.parse("text/plain"), memberNumber.toString()
        )

        val address = RequestBody.create(
            MediaType.parse("text/plain"), address
        )
        val addressDetail = RequestBody.create(
            MediaType.parse("text/plain"), addressDetail
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

        placeService.updatePlace(
            placeNumber,
            memberNumber,
            address,
            addressDetail,
            phoneNumber,
            content,
            latitude,
            longitude
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == SUCCESS) {
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
        val keywordNamesArray = JsonArray().apply {
            keywordNames.forEach {
                add(it)
            }
        }
        val placeKeywordNumbersArray = JsonArray().apply {
            placeKeywordNumbers.forEach {
                add(it)
            }
        }
        val jsonObject = JsonObject().apply {
            add("keywordName", keywordNamesArray)
            add("placeKeywordNumber", placeKeywordNumbersArray)
        }
        placeService.updateKeyword(placeNumber, jsonObject)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == SUCCESS) {
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
        val jsonArray = JsonArray().apply {
            openingTimes.forEach {
                add(it)
            }
        }

        val jsonObject = JsonObject().apply {
            add("openingTime", jsonArray)
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
                    if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
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
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
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
                    if (response.code() == SUCCESS) {
                        response.body()?.let { callback.onSuccess(it) }
                    }
                }

            })
    }

    override fun getKeywordRank(callback: PlaceCallback<List<KeywordResponse>>) {
        keywordService.getKeywordRank().enqueue(object : Callback<List<KeywordResponse>> {
            override fun onFailure(call: Call<List<KeywordResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<List<KeywordResponse>>,
                response: Response<List<KeywordResponse>>
            ) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                }
            }

        })
    }

    companion object {
        fun getInstance(placeApi: PlaceApi): PlaceRemoteDataSource =
            PlaceRemoteDataSourceImpl(
                placeApi
            )
    }
}