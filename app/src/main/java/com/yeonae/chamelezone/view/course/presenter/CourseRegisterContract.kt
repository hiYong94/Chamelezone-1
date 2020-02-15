package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.network.room.entity.UserEntity

interface CourseRegisterContract {

    interface View {
        var presenter: Presenter
        fun showMessage(message: String)
        fun showUserInfo(user: UserEntity)
    }

    interface Presenter {
        fun registerCourse(
            memberNumber: Int,
            placeNumber: List<Int>,
            title: String,
            content: String,
            images: List<String>)

        fun getUser()
    }
}