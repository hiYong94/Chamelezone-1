package com.yeonae.chamelezone.network.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConnection {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://13.209.136.122:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val memberService = retrofit.create<MemberApi>(MemberApi::class.java)
    val placeService = retrofit.create<PlaceApi>(PlaceApi::class.java)
    val courseService = retrofit.create<CourseApi>(CourseApi::class.java)
    val keywordService = retrofit.create<KeywordApi>(KeywordApi::class.java)
}