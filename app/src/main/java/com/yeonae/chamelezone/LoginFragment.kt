package com.yeonae.chamelezone

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    private val testEmail = "heimish_08@naver.com"
    private val testPassword = "1234"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_back.setOnClickListener {
            requireActivity().finish()
        }
        btn_find_email.setOnClickListener {
            (activity as LoginActivity).replace(FindEmailFragment(), true)
        }
        btn_find_password.setOnClickListener {
            (activity as LoginActivity).replace(FindPasswordFragment(), true)
        }
        btn_join.setOnClickListener {
            (activity as LoginActivity).replace(JoinFragment(), true)
        }
        btn_login.setOnClickListener {
            loginCheck("${edt_email.text}", "${edt_password.text}")
        }

    }

    private fun loginCheck(email: String, password: String) {
        if (email.isEmpty()) {
            Toast.makeText(context!!.applicationContext, "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(context!!.applicationContext, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
        } else {
            if (email == testEmail) {
                if (password == testPassword) {
                    Toast.makeText(context!!.applicationContext, "로그인 성공", Toast.LENGTH_LONG).show()
                } else {
                    showDialog()
                }
            } else {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val newFragment = AlertDialogFragment.newInstance(
            "입력하신 정보는 존재하지 않습니다."
        )
        newFragment.show(fragmentManager!!, "dialog")
    }

}