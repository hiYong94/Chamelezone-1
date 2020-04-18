package com.yeonae.chamelezone.data.repository.review

interface ReviewCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(message: String)
}