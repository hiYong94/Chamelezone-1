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
        callBack: CourseCallBack<String>
    ) {
        remoteDataSource.registerCourse(memberNumber, placeNumbers, title, content, image, callBack)
    }

    override fun getCourseList(callBack: CourseCallBack<List<CourseResponse>>) {
        remoteDataSource.getCourseList(callBack)
    }

    override fun getCourseDetail(courseNumber: Int, callBack: CourseCallBack<List<CourseResponse>>) {
        remoteDataSource.getCourseDetail(courseNumber, callBack)
    }

    override fun getMyCourseList(
        memberNumber: Int,
        callBack: CourseCallBack<List<CourseResponse>>
    ) {
        remoteDataSource.getMyCourseList(memberNumber, callBack)
    }

    override fun modifyCourse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCourse(courseNumber: Int, callBack: CourseCallBack<String>) {
        remoteDataSource.deleteCourse(courseNumber, callBack)
    }

    companion object {
        fun getInstance(remoteDataSource: CourseRemoteDataSource): CourseRepository =
            CourseRepositoryImpl(remoteDataSource)
    }
}