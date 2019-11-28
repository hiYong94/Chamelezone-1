package com.yeonae.chamelezone.view.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.login.LoginActivity
import com.yeonae.chamelezone.view.login.UserModifyActivity
import com.yeonae.chamelezone.view.mypage.mycourse.MyCourseActivity
import com.yeonae.chamelezone.view.mypage.myplace.MyPlaceActivity
import com.yeonae.chamelezone.view.mypage.myreview.MyReviewActivity
import kotlinx.android.synthetic.main.fragment_mypage.*

class MypageTabFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_mypage, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
    }
}