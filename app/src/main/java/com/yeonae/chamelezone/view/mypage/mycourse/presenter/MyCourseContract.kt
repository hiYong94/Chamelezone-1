package com.yeonae.chamelezone.view.mypage.mycourse.presenter

import com.yeonae.chamelezone.data.model.MyCourseItem
import com.yeonae.chamelezone.network.model.CourseResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

interface MyCourseContract {

    interface View {
        var presenter: Presenter
        fun showMyCourseList(response: List<MyCourseItem>)
        fun showUserInfo(user: UserEntity)
        fun showMessage(message: String)
        fun showDeleteResult(response: Boolean)
    }

    interface Presenter {
        fun getMyCourseList(memberNumber: Int)
        fun getUser()
        fun deleteCourse(courseNumber: Int, memberNumber: Int)
    }
}