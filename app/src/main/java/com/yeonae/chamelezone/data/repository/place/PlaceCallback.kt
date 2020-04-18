package com.yeonae.chamelezone.data.repository.place

interface PlaceCallback<T>{
    fun onSuccess(response: T)
    fun onFailure(message: String)
}