package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.data.model.CourseItem
import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.model.CourseResponse

class CoursePresenter(
    private val memberRepository: MemberRepository,
    private val courseRepository: CourseRepository,
    private val view: CourseContract.View
) : CourseContract.Presenter {
    override fun checkLogin() {
        memberRepository.checkLogin(object : MemberCallBack<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showResultView(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getCourseList() {
        courseRepository.getCourseList(object : CourseCallBack<List<CourseResponse>> {
            override fun onSuccess(response: List<CourseResponse>) {
                val items = mutableListOf<CourseItem>()
                for(i in response.indices){
                    items.add(response[i].toCourseItem())
                }
                view.showCourseList(items)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}