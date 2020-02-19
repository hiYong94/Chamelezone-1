package com.yeonae.chamelezone.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitConnection {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://13.209.136.122:3000")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val memberService = retrofit.create<MemberApi>(MemberApi::class.java)
    val placeService = retrofit.create<PlaceApi>(PlaceApi::class.java)
    val reviewService = retrofit.create<ReviewApi>(ReviewApi::class.java)
    val courseService = retrofit.create<CourseApi>(CourseApi::class.java)
    val keywordService = retrofit.create<KeywordApi>(KeywordApi::class.java)
}