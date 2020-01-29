package com.yeonae.chamelezone.data.repository.review

interface ReviewCallBack<T> {
    fun onSuccess(response: T)
    fun onFailure(message: String)
}