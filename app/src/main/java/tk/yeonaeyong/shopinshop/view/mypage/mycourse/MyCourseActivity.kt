package tk.yeonaeyong.shopinshop.view.mypage.mycourse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_course.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.MyCourseItem
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity
import tk.yeonaeyong.shopinshop.view.course.CourseDetailActivity
import tk.yeonaeyong.shopinshop.view.course.CourseModifyActivity
import tk.yeonaeyong.shopinshop.view.mypage.MoreButtonFragment
import tk.yeonaeyong.shopinshop.view.mypage.mycourse.adapter.MyCourseRvAdapter
import tk.yeonaeyong.shopinshop.view.mypage.mycourse.presenter.MyCourseContract
import tk.yeonaeyong.shopinshop.view.mypage.mycourse.presenter.MyCoursePresenter

class MyCourseActivity : AppCompatActivity(), MyCourseContract.View,
    MoreButtonFragment.OnModifyClickListener, MoreButtonFragment.OnDeleteClickListener {
    private val myCourseRvAdapter = MyCourseRvAdapter()
    override lateinit var presenter: MyCourseContract.Presenter
    var memberNumber: Int = 0
    lateinit var courseItem: MyCourseItem

    override fun showDeleteResult(response: Boolean) {
        if (response) {
            Log.d("courseDelete", response.toString())
        }
    }

    override fun onModifyClick() {
        showCourseModifyActivity()
    }

    override fun onDeleteClick() {
        presenter.deleteCourse(courseItem.courseNumber, memberNumber)
        myCourseRvAdapter.removeData(courseItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_course)
        setAdapter()

        presenter = MyCoursePresenter(
            Injection.memberRepository(), Injection.courseRepository(), this
        )

        presenter.getUser()

        myCourseRvAdapter.setOnClickListener(object : MyCourseRvAdapter.OnClickListener {
            override fun onClick(course: MyCourseItem) {
                val intent = Intent(this@MyCourseActivity, CourseDetailActivity::class.java)
                intent.putExtra(COURSE_NUMBER, course.courseNumber)
                startActivity(intent)
            }
        })

        myCourseRvAdapter.setMoreButtonListener(object : MyCourseRvAdapter.MoreButtonListener {
            override fun bottomSheetDialog(course: MyCourseItem) {
                courseItem = course
                showBottomSheet()
            }
        })

        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                presenter.getMyCourseList(memberNumber)
            }
        }
    }

    override fun showUserInfo(user: UserEntity) {
        memberNumber = user.userNumber ?: 0
        presenter.getMyCourseList(memberNumber)
    }

    override fun showMyCourseList(response: List<MyCourseItem>) {
        tv_message.visibility = View.GONE
        recycler_my_course.visibility = View.VISIBLE
        myCourseRvAdapter.addData(response)
    }

    override fun showMessage(message: String) {
        tv_message.visibility = View.VISIBLE
        recycler_my_course.visibility = View.GONE
        tv_message.text = message
    }

    private fun showCourseModifyActivity() {
        val intent = Intent(this, CourseModifyActivity::class.java)
        intent.putExtra("courseNumber", courseItem.courseNumber)
        intent.putExtra("memberNumber", memberNumber)
        startActivityForResult(intent, UPDATE_REQUEST_CODE)
    }

    private fun showBottomSheet() {
        val bottomSheetDialogFragment = MoreButtonFragment()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun setAdapter() {
        recycler_my_course.layoutManager = LinearLayoutManager(this)
        recycler_my_course.adapter = myCourseRvAdapter
    }

    companion object {
        const val UPDATE_REQUEST_CODE = 1
        private const val COURSE_NUMBER = "courseNumber"
    }
}