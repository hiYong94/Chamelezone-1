package com.yeonae.chamelezone.data

import com.yeonae.chamelezone.network.model.PlaceResponse

interface PlaceCallback{
    fun onSuccess(placeList: List<PlaceResponse>)
    fun onFailure(message: String)
}