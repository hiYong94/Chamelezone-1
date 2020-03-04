package com.yeonae.chamelezone.view.course

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.CourseResponse
import com.yeonae.chamelezone.view.course.presenter.CourseDetailContract
import com.yeonae.chamelezone.view.course.presenter.CourseDetailPresenter
import kotlinx.android.synthetic.main.activity_course_detail.*

class CourseDetailActivity : AppCompatActivity(), CourseDetailContract.View {
    override fun showCourseDetail(course: List<CourseResponse>) {
        Log.d("course", course.toString())
        iv_course_image.glideImageSet(
            IMAGE_RESOURCE + course[0].courseImage,
            iv_course_image.measuredWidth,
            iv_course_image.measuredHeight
        )
        tv_course_title.text = course[0].title
        tv_course_content.text = course[0].content
        iv_place_image1.glideImageSet(
            IMAGE_RESOURCE + course[0].placeImages,
            iv_place_image1.measuredWidth,
            iv_place_image1.measuredHeight
        )
        course[0].keywordName.forEach {
            if (it == course[0].keywordName[0]) {
                tv_first_keyword.text = it
            } else {
                tv_first_keyword.text = "${tv_first_keyword.text}${","} $it"
            }
        }
        tv_first_place_name.text = course[0].placeName
        tv_first_address.text = course[0].address

        if (course.size == 2) {
            layout_second_place.visibility = View.VISIBLE
            iv_place_image2.glideImageSet(
                IMAGE_RESOURCE + course[1].placeImages,
                iv_place_image2.measuredWidth,
                iv_place_image2.measuredHeight
            )
            course[1].keywordName.forEach {
                if (it == course[1].keywordName[0]) {
                    tv_second_keyword.text = it
                } else {
                    tv_second_keyword.text = "${tv_second_keyword.text}${","} $it"
                }
            }
            tv_second_place_name.text = course[1].placeName
            tv_second_address.text = course[1].address
        } else if (course.size == 3) {
            layout_second_place.visibility = View.VISIBLE
            iv_place_image2.glideImageSet(
                IMAGE_RESOURCE + course[1].placeImages,
                iv_place_image2.measuredWidth,
                iv_place_image2.measuredHeight
            )
            course[1].keywordName.forEach {
                if (it == course[1].keywordName[0]) {
                    tv_second_keyword.text = it
                } else {
                    tv_second_keyword.text = "${tv_second_keyword.text}${","} $it"
                }
            }
            tv_second_place_name.text = course[1].placeName
            tv_second_address.text = course[1].address
            iv_place_image3.glideImageSet(
                IMAGE_RESOURCE + course[2].placeImages,
                iv_place_image3.measuredWidth,
                iv_place_image3.measuredHeight
            )
            course[2].keywordName.forEach {
                if (it == course[2].keywordName[0]) {
                    tv_third_keyword.text = it
                } else {
                    tv_third_keyword.text = "${tv_third_keyword.text}${","} $it"
                }
            }
            tv_third_place_name.text = course[2].placeName
            tv_third_address.text = course[2].address
        }
    }

    override lateinit var presenter: CourseDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)
        presenter = CourseDetailPresenter(
            Injection.courseRepository(), this
        )
        val courseNumber = intent.getIntExtra("courseNumber", 0)
        presenter.getCourseDetail(courseNumber)
        Log.d("courseNumber", courseNumber.toString())

        btn_back.setOnClickListener {
            finish()
        }
    }
}