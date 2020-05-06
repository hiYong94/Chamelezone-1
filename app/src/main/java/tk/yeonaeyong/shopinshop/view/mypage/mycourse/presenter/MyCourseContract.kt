package tk.yeonaeyong.shopinshop.view.mypage.mycourse.presenter

import tk.yeonaeyong.shopinshop.data.model.MyCourseItem
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

interface MyCourseContract {

    interface View {
        var presenter: Presenter
        fun showMyCourseList(response: List<MyCourseItem>)
        fun showUserInfo(user: UserEntity)
        fun showMessage(message: String)
        fun showDeleteResult(response: Boolean)
    }

    interface Presenter {
        fun getMyCourseList(memberNumber: Int)
        fun getUser()
        fun deleteCourse(courseNumber: Int, memberNumber: Int)
    }
}