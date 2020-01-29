package com.yeonae.chamelezone.data.source.remote.course

import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.network.api.CourseApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.courseService

class CourseRemoteDataSourceImpl private constructor(private val courseApi: CourseApi) : CourseRemoteDataSource {
    override fun registerCourse(
        memberNumber: Int,
        placeNumber: Int,
        title: String,
        content: String,
        callBack: CourseCallBack<String>
    ) {

    }

    override fun getCourseList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCourseDetail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCourse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyCourse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}