package com.yeonae.chamelezone.view.course.presenter

import android.util.Log
import com.yeonae.chamelezone.data.repository.course.CourseCallback
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.data.repository.member.MemberRepository
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
        image: String
    ) {
        courseRepository.registerCourse(
            memberNumber,
            placeNumber,
            title,
            content,
            image,
            object : CourseCallback<String> {
                override fun onSuccess(response: String) {
                    view.showMessage(response)
                }

                override fun onFailure(message: String) {
                    Log.d("courseRegister", message)
                }

            })
    }

    override fun getUser() {
        memberRepository.getMember(object : MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                view.deliverUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}