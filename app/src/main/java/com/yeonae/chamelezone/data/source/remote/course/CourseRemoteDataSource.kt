package com.yeonae.chamelezone.data.source.remote.course

import com.yeonae.chamelezone.data.repository.course.CourseCallBack

interface CourseRemoteDataSource {
    fun registerCourse(
        memberNumber: Int,
        placeNumber: Int,
        title: String,
        content: String,
        callBack: CourseCallBack<String>
    )

    fun getCourseList()
    fun getCourseDetail()
    fun deleteCourse()
    fun modifyCourse()
}