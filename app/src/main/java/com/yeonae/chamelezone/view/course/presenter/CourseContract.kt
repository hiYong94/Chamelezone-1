package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.data.model.CourseItem

interface CourseContract {

    interface View {
        var presenter: Presenter
        fun showResultView(response: Boolean)
        fun showCourseList(items: List<CourseItem>)
    }

    interface Presenter {
        fun checkLogin()
        fun getCourseList()
    }
}