package com.yeonae.chamelezone.data.repository.course

import com.yeonae.chamelezone.network.model.CourseResponse

interface CourseRepository {
    fun registerCourse(
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        image: String,
        callback: CourseCallback<String>
    )

    fun getCourseList(callback: CourseCallback<List<CourseResponse>>)
    fun getCourseDetail(courseNumber: Int, callback: CourseCallback<List<CourseResponse>>)
    fun getMyCourseList(memberNumber: Int, callback: CourseCallback<List<CourseResponse>>)
    fun modifyCourse(
        courseNumber: Int,
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        image: String,
        imageNumber: Int,
        callback: CourseCallback<Boolean>
    )

    fun modifyCourse(
        courseNumber: Int,
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        savedImageName: String,
        callback: CourseCallback<Boolean>
    )

    fun deleteCourse(courseNumber: Int, memberNumber: Int, callback: CourseCallback<Boolean>)
}