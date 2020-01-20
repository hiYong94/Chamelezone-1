package com.yeonae.chamelezone.data.repository.member

interface MemberCallBack<T> {
    fun onSuccess(response: T)
    fun onFailure(message: String)
}