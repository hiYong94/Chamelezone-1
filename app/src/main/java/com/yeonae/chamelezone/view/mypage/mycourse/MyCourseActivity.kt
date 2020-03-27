package com.yeonae.chamelezone.view.mypage.mycourse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.MyCourseItem
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.course.CourseDetailActivity
import com.yeonae.chamelezone.view.course.CourseModifyActivity
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment
import com.yeonae.chamelezone.view.mypage.mycourse.adapter.MyCourseRvAdapter
import com.yeonae.chamelezone.view.mypage.mycourse.presenter.MyCourseContract
import com.yeonae.chamelezone.view.mypage.mycourse.presenter.MyCoursePresenter
import kotlinx.android.synthetic.main.activity_my_course.*

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

    override fun showUserInfo(user: UserEntity) {
        memberNumber = user.userNumber ?: 0
        presenter.getMyCourseList(memberNumber)
    }

    override fun showMyCourseList(response: List<MyCourseItem>) {
        layout_no_my_course.visibility = View.GONE
        layout_my_course.visibility = View.VISIBLE
        myCourseRvAdapter.addData(response)
    }

    override fun showMessage(message: String) {
        layout_no_my_course.visibility = View.VISIBLE
        layout_my_course.visibility = View.GONE
        tv_message.text = message
    }

    private fun showCourseModifyActivity() {
        val intent = Intent(this, CourseModifyActivity::class.java)
        intent.putExtra("courseNumber", courseItem.courseNumber)
        startActivity(intent)
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
        private const val COURSE_NUMBER = "courseNumber"
    }
}