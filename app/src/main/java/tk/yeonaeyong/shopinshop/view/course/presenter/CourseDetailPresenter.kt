package tk.yeonaeyong.shopinshop.view.course.presenter

import tk.yeonaeyong.shopinshop.data.model.CourseDetailItem
import tk.yeonaeyong.shopinshop.data.repository.course.CourseCallback
import tk.yeonaeyong.shopinshop.data.repository.course.CourseRepository
import tk.yeonaeyong.shopinshop.network.model.CourseResponse

class CourseDetailPresenter(
    private val repository: CourseRepository,
    private val view: CourseDetailContract.View
) : CourseDetailContract.Presenter {
    override fun getCourseDetail(courseNumber: Int) {
        repository.getCourseDetail(courseNumber, object :
            CourseCallback<List<CourseResponse>> {
            override fun onSuccess(response: List<CourseResponse>) {
                val courseDetailItem = mutableListOf<CourseDetailItem>()
                for (i in response.indices) {
                    courseDetailItem.add(response[i].toCourseDetailItem())
                }
                view.showCourseDetail(courseDetailItem)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}