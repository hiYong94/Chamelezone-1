package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.network.model.CourseResponse

class CourseDetailPresenter(
    private val repository: CourseRepository,
    private val view: CourseDetailContract.View
) : CourseDetailContract.Presenter {
    override fun getCourseDetail(courseNumber: Int) {
        repository.getCourseDetail(courseNumber, object : CourseCallBack<List<CourseResponse>> {
            override fun onSuccess(response: List<CourseResponse>) {
                view.showCourseDetail(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}