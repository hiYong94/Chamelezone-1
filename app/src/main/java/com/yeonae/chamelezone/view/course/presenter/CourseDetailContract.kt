package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.data.model.CourseDetailItem

interface CourseDetailContract {

    interface View {
        var presenter: Presenter
        fun showCourseDetail(courseList: List<CourseDetailItem>)
    }

    interface Presenter {
        fun getCourseDetail(courseNumber: Int)
    }
}