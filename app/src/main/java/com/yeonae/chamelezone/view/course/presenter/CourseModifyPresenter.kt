package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.network.model.CourseResponse

class CourseModifyPresenter(
    private val repository: CourseRepository,
    private val view: CourseModifyContract.View
) : CourseModifyContract.Presenter {
    override fun getCourseDetail(courseNumber: Int) {
        repository.getCourseDetail(courseNumber, object : CourseCallBack<List<CourseResponse>> {
            override fun onSuccess(response: List<CourseResponse>) {
                view.showCourseDetail(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun modifyCourse(
        courseNumber: Int,
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        image: String,
        imageNumber: Int
    ) {
        repository.modifyCourse(
            courseNumber,
            memberNumber,
            placeNumbers,
            title,
            content,
            image,
            imageNumber,
            object : CourseCallBack<Boolean> {
                override fun onSuccess(response: Boolean) {
                    view.showMessage(response)
                }

                override fun onFailure(message: String) {

                }

            })
    }
}