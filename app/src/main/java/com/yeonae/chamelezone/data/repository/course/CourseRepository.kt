package com.yeonae.chamelezone.data.repository.course

interface CourseRepository {
    fun registerCourse(
        memberNumber: Int,
        placeNumber: Int,
        title: String,
        content: String,
        callBack: CourseCallBack<String>
    )

    fun getCourseList()
    fun getCourseDetail()
    fun getMyCourseList()
    fun deleteCourse()
    fun modifyCourse()
}