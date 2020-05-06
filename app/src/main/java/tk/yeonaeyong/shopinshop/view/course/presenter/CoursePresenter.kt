package tk.yeonaeyong.shopinshop.view.course.presenter

import tk.yeonaeyong.shopinshop.data.model.CourseItem
import tk.yeonaeyong.shopinshop.data.repository.course.CourseCallback
import tk.yeonaeyong.shopinshop.data.repository.course.CourseRepository
import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.network.model.CourseResponse

class CoursePresenter(
    private val memberRepository: MemberRepository,
    private val courseRepository: CourseRepository,
    private val view: CourseContract.View
) : CourseContract.Presenter {
    override fun checkLogin() {
        memberRepository.checkLogin(object :
            MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showResultView(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getCourseList() {
        courseRepository.getCourseList(object :
            CourseCallback<List<CourseResponse>> {
            override fun onSuccess(response: List<CourseResponse>) {
                val items = mutableListOf<CourseItem>()
                for (i in response.indices) {
                    items.add(response[i].toCourseItem())
                }
                view.showCourseList(items)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}