package com.yeonae.chamelezone.data.repository.place

interface PlaceCallBack<T>{
    fun onSuccess(response: T)
    fun onFailure(message: String)
}