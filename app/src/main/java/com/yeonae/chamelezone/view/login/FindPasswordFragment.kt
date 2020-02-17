package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.DialogFragment
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.fragment_find_password.*

class FindPasswordFragment : Fragment() {
    private val testEmail = "heimish_08@naver.com"
    private val testPhone = "01049403065"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_find_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_find_password.setOnClickListener {
            passwordCheck("${edt_email.text}", "${edt_phone.text}")
        }

        btn_back.setOnClickListener {
            (activity as LoginActivity).back()
        }
    }

    private fun passwordCheck(email: String, phone: String) {
        if (email == testEmail) {
            if (phone == testPhone) {
                Toast.makeText(
                    context!!.applicationContext,
                    "임시 비밀번호가 이메일로 발송되었습니다.",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                showDialog()
            }
        } else {
            showDialog()
        }
    }

    private fun showDialog() {
        val newFragment = DialogFragment.newInstance(
            "입력하신 정보는 존재하지 않습니다."
        )
        newFragment.show(fragmentManager!!, "dialog")
    }
}