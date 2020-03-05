package com.yeonae.chamelezone.view.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.MultipleDialogFragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.Context.APPLICATION_CONTEXT
import com.yeonae.chamelezone.view.login.LoginActivity
import com.yeonae.chamelezone.view.mypage.mycourse.MyCourseActivity
import com.yeonae.chamelezone.view.mypage.myplace.MyPlaceActivity
import com.yeonae.chamelezone.view.mypage.myreview.MyReviewActivity
import com.yeonae.chamelezone.view.mypage.presenter.MypageContract
import com.yeonae.chamelezone.view.mypage.presenter.MypagePresenter
import kotlinx.android.synthetic.main.fragment_mypage_tab.*

class MypageTabFragment : Fragment(), MypageContract.View {
    var memberNumber: Int = 0

    override fun showUserInfo(user: UserEntity) {
        btn_nick_name.text = user.nickname
        memberNumber = user.userNumber ?: 0
    }

    override fun showResultView(response: Boolean) {
        if (response) {
            layout_nick_name.visibility = View.VISIBLE
            btn_login.visibility = View.GONE
            layout_user_modify.visibility = View.VISIBLE
            layout_my_review.visibility = View.VISIBLE
            layout_my_place.visibility = View.VISIBLE
            layout_my_course.visibility = View.VISIBLE
            layout_logout.visibility = View.VISIBLE
            layout_user_delete.visibility = View.VISIBLE
            presenter.getUser()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override lateinit var presenter: MypageContract.Presenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_mypage_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = MypagePresenter(
            Injection.memberRepository(APPLICATION_CONTEXT), this
        )

        btn_login.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        btn_user_modify.setOnClickListener {
            val intent = Intent(requireContext(), UserModifyActivity::class.java)
            startActivity(intent)
        }

        btn_my_review.setOnClickListener {
            val intent = Intent(requireContext(), MyReviewActivity::class.java)
            startActivity(intent)
        }

        btn_my_place.setOnClickListener {
            val intent = Intent(requireContext(), MyPlaceActivity::class.java)
            startActivity(intent)
        }

        btn_my_course.setOnClickListener {
            val intent = Intent(requireContext(), MyCourseActivity::class.java)
            startActivity(intent)
        }

        btn_intro.setOnClickListener {
            val intent = Intent(requireContext(), IntroActivity::class.java)
            startActivity(intent)
        }

        btn_personal_info.setOnClickListener {
            val intent = Intent(requireContext(), PersonaInfoActivity::class.java)
            startActivity(intent)
        }

        btn_user_delete.setOnClickListener {
            val newFragment = MultipleDialogFragment.newInstance(
                getString(R.string.really_delete), object :
                    MultipleDialogFragment.OnClickListener {
                    override fun onClick() {
                        presenter.deleteUser(memberNumber)
                        presenter.logout()
                        layout_nick_name.visibility = View.GONE
                        btn_login.visibility = View.VISIBLE
                        layout_user_modify.visibility = View.GONE
                        layout_my_review.visibility = View.GONE
                        layout_my_place.visibility = View.GONE
                        layout_my_course.visibility = View.GONE
                        layout_logout.visibility = View.GONE
                        layout_user_delete.visibility = View.GONE
                    }
                }
            )
            fragmentManager?.let { newFragment.show(it, "dialog") }
        }

        btn_logout.setOnClickListener {
            val newFragment = MultipleDialogFragment.newInstance(
                getString(R.string.really_logout), object :
                    MultipleDialogFragment.OnClickListener {
                    override fun onClick() {
                        presenter.logout()
                        layout_nick_name.visibility = View.GONE
                        btn_login.visibility = View.VISIBLE
                        layout_user_modify.visibility = View.GONE
                        layout_my_review.visibility = View.GONE
                        layout_my_place.visibility = View.GONE
                        layout_my_course.visibility = View.GONE
                        layout_logout.visibility = View.GONE
                        layout_user_delete.visibility = View.GONE
                    }
                }
            )
            fragmentManager?.let { newFragment.show(it, "dialog") }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.checkLogin()
    }

}