package com.yeonae.chamelezone.data.repository.member

interface MemberCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(message: String)
}