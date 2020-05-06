package tk.yeonaeyong.shopinshop.view.mypage.mycourse.presenter

import tk.yeonaeyong.shopinshop.data.model.MyCourseItem
import tk.yeonaeyong.shopinshop.data.repository.course.CourseCallback
import tk.yeonaeyong.shopinshop.data.repository.course.CourseRepository
import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.network.model.CourseResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

class MyCoursePresenter(
    private val memberRepository: MemberRepository,
    private val repository: CourseRepository,
    private val view: MyCourseContract.View
) : MyCourseContract.Presenter {
    override fun getMyCourseList(memberNumber: Int) {
        repository.getMyCourseList(memberNumber, object :
            CourseCallback<List<CourseResponse>> {
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
        memberRepository.getMember(object :
            MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                view.showUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deleteCourse(courseNumber: Int, memberNumber: Int) {
        repository.deleteCourse(courseNumber, memberNumber, object :
            CourseCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showDeleteResult(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}