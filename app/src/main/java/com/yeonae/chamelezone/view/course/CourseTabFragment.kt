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
import com.yeonae.chamelezone.data.model.CourseItem
import com.yeonae.chamelezone.view.course.adapter.CourseTabRvAdapter
import com.yeonae.chamelezone.view.course.presenter.CourseContract
import com.yeonae.chamelezone.view.course.presenter.CoursePresenter
import com.yeonae.chamelezone.view.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_course_tab.*

class CourseTabFragment : Fragment(), CourseContract.View {
    override lateinit var presenter: CourseContract.Presenter
    private val courseTabRvAdapter = CourseTabRvAdapter()

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

    override fun showCourseList(item: List<CourseItem>) {
        courseTabRvAdapter.addData(item)
    }

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
            override fun onClick(course: CourseItem) {
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