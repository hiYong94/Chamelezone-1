package com.yeonae.chamelezone.data.repository.like

interface LikeCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(message: String)
}