package com.yeonae.chamelezone.data.source.remote.course

import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.network.model.CourseResponse

interface CourseRemoteDataSource {
    fun registerCourse(
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        image: String,
        callBack: CourseCallBack<String>
    )

    fun getCourseList(callBack: CourseCallBack<List<CourseResponse>>)
    fun getCourseDetail(courseNumber: Int, callBack: CourseCallBack<List<CourseResponse>>)
    fun getMyCourseList(memberNumber: Int, callBack: CourseCallBack<List<CourseResponse>>)
    fun modifyCourse(
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        image: String,
        callBack: CourseCallBack<String>
    )

    fun deleteCourse(courseNumber: Int, memberNumber: Int, callBack: CourseCallBack<String>)
}