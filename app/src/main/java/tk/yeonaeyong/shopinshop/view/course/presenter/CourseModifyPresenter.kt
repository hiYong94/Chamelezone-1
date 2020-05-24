package tk.yeonaeyong.shopinshop.view.course.presenter

import tk.yeonaeyong.shopinshop.data.repository.course.CourseCallback
import tk.yeonaeyong.shopinshop.data.repository.course.CourseRepository
import tk.yeonaeyong.shopinshop.network.model.CourseResponse

class CourseModifyPresenter(
    private val repository: CourseRepository,
    private val view: CourseModifyContract.View
) : CourseModifyContract.Presenter {
    override fun getCourseDetail(courseNumber: Int) {
        repository.getCourseDetail(courseNumber, object :
            CourseCallback<List<CourseResponse>> {
            override fun onSuccess(response: List<CourseResponse>) {
                view.showCourseDetail(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun modifyCourse(
        courseNumber: Int,
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        image: String,
        imageNumber: Int
    ) {
        repository.modifyCourse(
            courseNumber,
            memberNumber,
            placeNumbers,
            title,
            content,
            image,
            imageNumber,
            object :
                CourseCallback<Boolean> {
                override fun onSuccess(response: Boolean) {
                    view.showMessage(response)
                }

                override fun onFailure(message: String) {

                }

            })
    }

    override fun modifyCourse(
        courseNumber: Int,
        memberNumber: Int,
        placeNumbers: List<Int>,
        title: String,
        content: String,
        savedImageName: String
    ) {
        repository.modifyCourse(
            courseNumber,
            memberNumber,
            placeNumbers,
            title,
            content,
            savedImageName,
            object :
                CourseCallback<Boolean> {
                override fun onSuccess(response: Boolean) {
                    view.showMessage(response)
                }

                override fun onFailure(message: String) {

                }

            })
    }
}