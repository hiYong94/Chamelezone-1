package com.yeonae.chamelezone.view.mypage.mycourse.presenter

import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.network.model.CourseResponse

class MyCoursePresenter(
    private val repository: CourseRepository,
    private val view: MyCourseContract.View
) : MyCourseContract.Presenter {
    override fun getMyCourseList(memberNumber: Int) {
        repository.getMyCourseList(memberNumber, object : CourseCallBack<List<CourseResponse>> {
            override fun onSuccess(response: List<CourseResponse>) {
                view.showMyCourseList(response)
            }

            override fun onFailure(message: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}