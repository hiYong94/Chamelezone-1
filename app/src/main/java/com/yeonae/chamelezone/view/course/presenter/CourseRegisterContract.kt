package com.yeonae.chamelezone.view.course.presenter

interface CourseRegisterContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
    }

    interface Presenter {
        fun registerCourse(
            memberNumber: Int,
            placeNumber: Int,
            title: String,
            content: String)
    }
}