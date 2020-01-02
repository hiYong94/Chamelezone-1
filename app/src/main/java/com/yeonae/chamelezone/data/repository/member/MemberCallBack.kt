package com.yeonae.chamelezone.data.repository.member

interface MemberCallBack{
    fun onSuccess(message: String)
    fun onFailure(message: String)
}