package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.network.model.CourseResponse

class CourseRegisterPresenter(
    private val repository: CourseRepository,
    private val view: CourseRegisterContract.View
) : CourseRegisterContract.Presenter {
    override fun registerCourse(
        memberNumber: Int,
        placeNumber: Int,
        title: String,
        image: String,
        content: String
    ) {
        repository.registerCourse(memberNumber, placeNumber, title, content, image, object : CourseCallBack<String>{
            override fun onSuccess(response: String) {
                view.showMessage(response)
            }

            override fun onFailure(message: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}