package com.yeonae.chamelezone.view.mypage.mycourse.presenter

import com.yeonae.chamelezone.data.model.MyCourseItem
import com.yeonae.chamelezone.data.repository.course.CourseCallBack
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.model.CourseResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity

class MyCoursePresenter(
    private val memberRepository: MemberRepository,
    private val repository: CourseRepository,
    private val view: MyCourseContract.View
) : MyCourseContract.Presenter {
    override fun getMyCourseList(memberNumber: Int) {
        repository.getMyCourseList(memberNumber, object : CourseCallBack<List<CourseResponse>> {
            override fun onSuccess(response: List<CourseResponse>) {
                val items = mutableListOf<MyCourseItem>()
                for (i in response.indices) {
                    items.add(response[i].toMyCourseItem())
                }
                view.showMyCourseList(items)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
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

    override fun deleteCourse(courseNumber: Int, memberNumber: Int) {
        repository.deleteCourse(courseNumber, memberNumber, object : CourseCallBack<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showDeleteResult(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}