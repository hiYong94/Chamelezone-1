package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.data.model.CourseDetailItem
import com.yeonae.chamelezone.data.repository.course.CourseCallback
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.network.model.CourseResponse

class CourseDetailPresenter(
    private val repository: CourseRepository,
    private val view: CourseDetailContract.View
) : CourseDetailContract.Presenter {
    override fun getCourseDetail(courseNumber: Int) {
        repository.getCourseDetail(courseNumber, object : CourseCallback<List<CourseResponse>> {
            override fun onSuccess(response: List<CourseResponse>) {
                val courseDetailItem = mutableListOf<CourseDetailItem>()
                for (i in response.indices) {
                    courseDetailItem.add(response[i].toCourseDetailItem())
                }
                view.showCourseDetail(courseDetailItem)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}