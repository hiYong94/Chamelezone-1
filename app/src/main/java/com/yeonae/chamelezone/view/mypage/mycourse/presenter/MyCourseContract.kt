package com.yeonae.chamelezone.view.mypage.mycourse.presenter

import com.yeonae.chamelezone.network.model.CourseResponse

interface MyCourseContract {

    interface View {
        var presenter: Presenter
        fun showMyCourseList(response: List<CourseResponse>)
    }

    interface Presenter {
        fun getMyCourseList(memberNumber: Int)
    }
}