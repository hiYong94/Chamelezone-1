package com.yeonae.chamelezone.data.repository.course

import com.yeonae.chamelezone.network.model.CourseResponse

interface CourseRepository {
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
    fun modifyCourse()
    fun deleteCourse(courseNumber: Int, memberNumber: Int, callBack: CourseCallBack<String>)
}