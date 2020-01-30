package com.yeonae.chamelezone.data.repository.course

import com.yeonae.chamelezone.data.source.remote.course.CourseRemoteDataSource

class CourseRepositoryImpl private constructor(private val remoteDataSource: CourseRemoteDataSource) :
    CourseRepository {
    override fun registerCourse(
        memberNumber: Int,
        placeNumber: Int,
        title: String,
        content: String,
        callBack: CourseCallBack<String>
    ) {
        remoteDataSource.registerCourse(memberNumber, placeNumber, title, content, callBack)
    }

    override fun getCourseList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCourseDetail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMyCourseList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCourse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyCourse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun getInstance(remoteDataSource: CourseRemoteDataSource): CourseRepository =
            CourseRepositoryImpl(remoteDataSource)
    }

}