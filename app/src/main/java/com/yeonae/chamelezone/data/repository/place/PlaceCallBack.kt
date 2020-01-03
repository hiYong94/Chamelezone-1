package com.yeonae.chamelezone.data.repository.place

interface PlaceCallBack{
    fun onSuccess(message: String)
    fun onFailure(message: String)
}