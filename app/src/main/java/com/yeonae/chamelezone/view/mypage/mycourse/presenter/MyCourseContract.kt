package com.yeonae.chamelezone.view.mypage.mycourse.presenter

import com.yeonae.chamelezone.network.model.CourseResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface MyCourseContract {

    interface View {
        var presenter: Presenter
        fun showMyCourseList(response: List<CourseResponse>)
        fun showUserInfo(user: UserEntity)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun getMyCourseList(memberNumber: Int)
        fun getUser()
    }
}