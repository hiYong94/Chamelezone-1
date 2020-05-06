package tk.yeonaeyong.shopinshop.view.course.presenter

import tk.yeonaeyong.shopinshop.network.model.CourseResponse

interface CourseModifyContract {

    interface View {
        var presenter: Presenter
        fun showCourseDetail(courseList: List<CourseResponse>)
        fun showMessage(response: Boolean)
    }

    interface Presenter {
        fun getCourseDetail(courseNumber: Int)
        fun modifyCourse(
            courseNumber: Int,
            memberNumber: Int,
            placeNumbers: List<Int>,
            title: String,
            content: String,
            image: String,
            imageNumber: Int
        )

        fun modifyCourse(
            courseNumber: Int,
            memberNumber: Int,
            placeNumbers: List<Int>,
            title: String,
            content: String,
            savedImageName: String
        )
    }
}