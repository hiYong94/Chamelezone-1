package com.yeonae.chamelezone.data.repository.course

interface CourseCallBack<T> {
    fun onSuccess(response: T)
    fun onFailure(message: String)
}