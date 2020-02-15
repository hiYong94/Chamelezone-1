package com.yeonae.chamelezone.view.course.presenter

import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.model.CourseResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class CourseRegisterPresenter(
    private val memberRepository: MemberRepository,
    private val courseRepository: CourseRepository,
    private val view: CourseRegisterContract.View
) : CourseRegisterContract.Presenter {
    override fun registerCourse(
        memberNumber: Int,
        placeNumber: List<Int>,
        title: String,
        content: String,
        images: List<String>
    ) {
        courseRepository.registerCourse(memberNumber, placeNumber, title, content, images, object : CourseCallBack<String>{
            override fun onSuccess(response: String) {
                view.showMessage(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getUser() {
        memberRepository.getMember(object : MemberCallBack<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                view.showUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}