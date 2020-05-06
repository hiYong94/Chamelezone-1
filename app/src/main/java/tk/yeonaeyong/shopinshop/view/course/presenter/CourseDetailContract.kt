package tk.yeonaeyong.shopinshop.view.course.presenter

import tk.yeonaeyong.shopinshop.data.model.CourseDetailItem

interface CourseDetailContract {

    interface View {
        var presenter: Presenter
        fun showCourseDetail(courseList: List<CourseDetailItem>)
    }

    interface Presenter {
        fun getCourseDetail(courseNumber: Int)
    }
}