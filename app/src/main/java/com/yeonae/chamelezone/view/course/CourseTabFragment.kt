package com.yeonae.chamelezone.view.course

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Course
import com.yeonae.chamelezone.network.model.CourseResponse
import com.yeonae.chamelezone.view.course.adapter.CourseTabRvAdapter
import com.yeonae.chamelezone.view.course.presenter.CourseContract
import com.yeonae.chamelezone.view.course.presenter.CoursePresenter
import com.yeonae.chamelezone.view.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_course_tab.*

class CourseTabFragment : Fragment(), CourseContract.View {
    override fun showResultView(response: Boolean) {
        if (response) {
            btn_register.setOnClickListener {
                val intent = Intent(requireContext(), CourseRegisterActivity::class.java)
                startActivity(intent)
            }
        } else {
            btn_register.setOnClickListener {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun showCourseList(courseList: List<CourseResponse>) {
        //courseTabRvAdapter.addData(courseList)
    }

    override lateinit var presenter: CourseContract.Presenter
    private val courseList = arrayListOf(
        Course("익선동 데이트 코스", "2019-11-29", "yeonjae22", "", "place1"),
        Course("용권이의 코스", "2019-11-28", "hiyong", "", "place2"),
        Course("책을 좋아하는 사람을 위한 코스", "2019-11-27", "Lsunae", "", "place3")
    )
    private val courseTabRvAdapter = CourseTabRvAdapter(courseList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_course_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = CoursePresenter(
            Injection.memberRepository(requireContext()), Injection.courseRepository(), this
        )
        presenter.getCourseList()
        setAdapter()

        courseTabRvAdapter.setOnClickListener(object : CourseTabRvAdapter.OnClickListener {
            override fun onClick(course: Course) {
                val intent = Intent(requireContext(), CourseDetailActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun setAdapter() {
        recycler_course.layoutManager = LinearLayoutManager(context)
        recycler_course.adapter = courseTabRvAdapter
    }

    override fun onResume() {
        super.onResume()
        presenter.checkLogin()
    }
}