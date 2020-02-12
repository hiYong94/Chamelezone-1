package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.network.model.CourseResponse

interface CourseContract {

    interface View {
        var presenter: Presenter
        fun showResultView(response: Boolean)
        fun showCourseList(courseList: List<CourseResponse>)
    }

    interface Presenter {
        fun checkLogin()
        fun getCourseList()
    }
}