package com.yeonae.chamelezone.network.api

import com.yeonae.chamelezone.network.model.KeywordResponse
import retrofit2.Call
import retrofit2.http.GET

interface KeywordApi {
    @GET("/keyword")
    fun getKeywordList(
    ): Call<List<KeywordResponse>>
}