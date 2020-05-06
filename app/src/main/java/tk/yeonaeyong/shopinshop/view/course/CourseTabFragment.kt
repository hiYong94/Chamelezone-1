package tk.yeonaeyong.shopinshop.view.course

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_course_tab.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.CourseItem
import tk.yeonaeyong.shopinshop.view.course.adapter.CourseTabRvAdapter
import tk.yeonaeyong.shopinshop.view.course.presenter.CourseContract
import tk.yeonaeyong.shopinshop.view.course.presenter.CoursePresenter
import tk.yeonaeyong.shopinshop.view.login.LoginActivity

class CourseTabFragment : Fragment(), CourseContract.View, SwipeRefreshLayout.OnRefreshListener {
    override lateinit var presenter: CourseContract.Presenter
    private val courseTabRvAdapter = CourseTabRvAdapter()

    override fun onRefresh() {
        presenter.getCourseList()
    }

    override fun showResultView(response: Boolean) {
        if (response) {
            btn_register.setOnClickListener {
                val intent = Intent(requireContext(), CourseRegisterActivity::class.java)
                startActivityForResult(intent, UPDATE_REQUEST_CODE)
            }
        } else {
            btn_register.setOnClickListener {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                presenter.getCourseList()
            }
        }
    }

    override fun showCourseList(items: List<CourseItem>) {
        courseTabRvAdapter.addData(items)
        swipe_course.isRefreshing = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_course_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipe_course.setOnRefreshListener(this)
        presenter = CoursePresenter(
            Injection.memberRepository(), Injection.courseRepository(), this
        )
        presenter.getCourseList()
        setAdapter()

        courseTabRvAdapter.setOnClickListener(object : CourseTabRvAdapter.OnClickListener {
            override fun onClick(course: CourseItem) {
                val intent = Intent(requireContext(), CourseDetailActivity::class.java)
                intent.putExtra("courseNumber", course.courseNumber)
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

    companion object {
        const val UPDATE_REQUEST_CODE = 1
    }
}