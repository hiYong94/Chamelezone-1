package tk.yeonaeyong.shopinshop.view.course.presenter

import android.util.Log
import tk.yeonaeyong.shopinshop.data.repository.course.CourseCallback
import tk.yeonaeyong.shopinshop.data.repository.course.CourseRepository
import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

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
            object :
                CourseCallback<String> {
                override fun onSuccess(response: String) {
                    view.showMessage(response)
                }

                override fun onFailure(message: String) {
                    Log.d("courseRegister", message)
                }

            })
    }

    override fun getUser() {
        memberRepository.getMember(object :
            MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                view.deliverUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}