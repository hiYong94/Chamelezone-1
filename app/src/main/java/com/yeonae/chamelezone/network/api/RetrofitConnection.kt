package com.yeonae.chamelezone.network.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnection {
    private var retrofit = Retrofit.Builder()
        .baseUrl("http://13.209.136.122:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service = retrofit.create<RetrofitInterface>(RetrofitInterface::class.java)
}