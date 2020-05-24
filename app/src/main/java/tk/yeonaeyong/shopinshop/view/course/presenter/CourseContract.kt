package tk.yeonaeyong.shopinshop.view.course.presenter

import tk.yeonaeyong.shopinshop.data.model.CourseItem

interface CourseContract {

    interface View {
        var presenter: Presenter
        fun showResultView(response: Boolean)
        fun showCourseList(items: List<CourseItem>)
    }

    interface Presenter {
        fun checkLogin()
        fun getCourseList()
    }
}