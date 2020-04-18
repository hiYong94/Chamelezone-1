package com.yeonae.chamelezone.data.repository.course

import com.yeonae.chamelezone.data.source.remote.course.CourseRemoteDataSource
import com.yeonae.chamelezone.network.model.CourseResponse

class CourseRepositoryImpl private constructor(private val remoteDataSource: CourseRemoteDataSource) :
    CourseRepository {
    override fun registerCourse(
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        image: String,
        callback: CourseCallback<String>
    ) {
        remoteDataSource.registerCourse(memberNumber, placeNumbers, title, content, image, callback)
    }

    override fun getCourseList(callback: CourseCallback<List<CourseResponse>>) {
        remoteDataSource.getCourseList(callback)
    }

    override fun getCourseDetail(
        courseNumber: Int,
        callback: CourseCallback<List<CourseResponse>>
    ) {
        remoteDataSource.getCourseDetail(courseNumber, callback)
    }

    override fun getMyCourseList(
        memberNumber: Int,
        callback: CourseCallback<List<CourseResponse>>
    ) {
        remoteDataSource.getMyCourseList(memberNumber, callback)
    }

    override fun modifyCourse(
        courseNumber: Int,
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        image: String,
        imageNumber: Int,
        callback: CourseCallback<Boolean>
    ) {
        remoteDataSource.modifyCourse(
            courseNumber,
            memberNumber,
            placeNumbers,
            title,
            content,
            image,
            imageNumber,
            callback
        )
    }

    override fun modifyCourse(
        courseNumber: Int,
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        imageNumber: Int,
        savedImageName: String,
        callback: CourseCallback<Boolean>
    ) {
        remoteDataSource.modifyCourse(
            courseNumber,
            memberNumber,
            placeNumbers,
            title,
            content,
            imageNumber,
            savedImageName,
            callback
        )
    }

    override fun deleteCourse(
        courseNumber: Int,
        memberNumber: Int,
        callback: CourseCallback<Boolean>
    ) {
        remoteDataSource.deleteCourse(courseNumber, memberNumber, callback)
    }

    companion object {
        fun getInstance(remoteDataSource: CourseRemoteDataSource): CourseRepository =
            CourseRepositoryImpl(remoteDataSource)
    }
}