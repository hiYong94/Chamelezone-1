package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.network.model.CourseResponse

class CoursePresenter(
    private val repository: CourseRepository,
    private val view: CourseContract.View
) : CourseContract.Presenter {
    override fun getCourseList() {
        repository.getCourseList(object : CourseCallBack<List<CourseResponse>>{
            override fun onSuccess(response: List<CourseResponse>) {
                view.showCourseList(response)
            }

            override fun onFailure(message: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}