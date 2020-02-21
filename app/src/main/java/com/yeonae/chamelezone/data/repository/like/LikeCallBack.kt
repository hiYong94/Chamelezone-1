package com.yeonae.chamelezone.data.repository.like

interface LikeCallBack<T> {
    fun onSuccess(response: T)
    fun onFailure(message: String)
}