package com.yeonae.chamelezone.view.mypage.mycourse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Course
import com.yeonae.chamelezone.view.course.CourseDetailActivity
import com.yeonae.chamelezone.view.mypage.mycourse.adapter.MyCourseRvAdapter
import kotlinx.android.synthetic.main.activity_my_course.*

class MyCourseActivity : AppCompatActivity() {
    private val myCourseList = arrayListOf(
        Course(
            "익선동 데이트 코스",
            "2019-11-29",
            "yeonjae22",
            "익선동에서 다양한 경험을 할 수 있어요. 제가 제일 자주 가는 코스입니다. 연재는 지금 졸리다. 마카롱이 매우 먹고싶다. 연재는 퇴근하고 싶다." +
                    "연재는 오늘 쇼핑을 할거다! 연재는 혼자 영등포가서 쇼핑할거다^^ 과연 4줄일까"
        ),
        Course("용권이의 코스", "2019-11-28", "hiyong", "용애용애"),
        Course(
            "선애와 연재처럼 책을 좋아하는 사람을 위한 코스",
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
                val intent = Intent(this@MyCourseActivity, CourseDetailActivity::class.java)
                startActivity(intent)
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