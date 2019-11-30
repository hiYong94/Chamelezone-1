package com.yeonae.chamelezone.view.mypage.mycourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Course
import com.yeonae.chamelezone.view.mypage.mycourse.adapter.MyCourseRvAdapter
import kotlinx.android.synthetic.main.activity_my_course.*

class MyCourseActivity : AppCompatActivity() {
    private val myCourseList = arrayListOf(
        Course(
            "익선동 데이트 코스",
            "2019-11-29",
            "yeonjae22",
            "익선동에서 다양한 경험을 할 수 있어요."
        ),
        Course("용권이의 코스", "2019-11-28", "hiyong", "용애용애"),
        Course(
            "책을 좋아하는 사람을 위한 코스",
            "2019-11-27",
            "Lsunae",
            "선애바보"
        )
    )
    private val myCourseRvAdapter = MyCourseRvAdapter(myCourseList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_course)
        setAdapter()

        myCourseRvAdapter.setOnClickListener(object : MyCourseRvAdapter.OnClickListener {
            override fun onClick(course: Course) {

            }
        })

        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun setAdapter() {
        recycler_my_course.layoutManager = LinearLayoutManager(this)
        recycler_my_course.adapter = myCourseRvAdapter
    }
}