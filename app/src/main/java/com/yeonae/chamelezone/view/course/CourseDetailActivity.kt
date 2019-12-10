package com.yeonae.chamelezone.view.course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.activity_course_detail.*

class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        btn_back.setOnClickListener {
            finish()
        }
    }
}