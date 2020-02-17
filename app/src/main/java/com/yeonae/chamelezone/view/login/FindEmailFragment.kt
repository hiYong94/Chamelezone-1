package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.DialogFragment
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.fragment_find_email.*

class FindEmailFragment : Fragment() {
    private val testEmail = "heimish_08@naver.com"
    private val testName = "yeonjae"
    private val testPhone = "01049403065"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_find_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_find_email.setOnClickListener {
            emailCheck("${edt_name.text}", "${edt_phone.text}")
        }
        btn_back.setOnClickListener {
            (activity as LoginActivity).back()
        }
    }

    private fun emailCheck(name: String, phone: String) {
        if (name == testName) {
            if (phone == testPhone) {
                (activity as LoginActivity).replace(
                    ConfirmEmailFragment.newInstance(
                    testEmail), true)
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