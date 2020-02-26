package com.yeonae.chamelezone.view.course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.CourseResponse
import com.yeonae.chamelezone.view.course.presenter.CourseDetailContract
import com.yeonae.chamelezone.view.course.presenter.CourseDetailPresenter
import kotlinx.android.synthetic.main.activity_course_detail.*

class CourseDetailActivity : AppCompatActivity(), CourseDetailContract.View {
    override fun showCourseDetail(course: CourseResponse) {
        tv_course_title.text = course.title
        tv_course_content.text = course.content
    }

    override lateinit var presenter: CourseDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)
        presenter = CourseDetailPresenter(
            Injection.courseRepository(), this
        )

        btn_back.setOnClickListener {
            finish()
        }
    }
}