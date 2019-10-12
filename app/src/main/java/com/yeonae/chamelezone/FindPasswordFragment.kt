package com.yeonae.chamelezone

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_find_password.*

class FindPasswordFragment : Fragment() {
    val testEmail = "heimish_08@naver.com"
    val testPhone = "01049403065"

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
            (activity as LoginActivity).replace(LoginFragment())
        }
    }

    fun passwordCheck(email: String, phone: String) {
        if (email == testEmail) {
            if (phone == testPhone) {
                Toast.makeText(
                    context!!.applicationContext,
                    "임시 비밀번호가 이메일로 발송되었습니다.",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                alertdialog()
            }
        } else {
            alertdialog()
        }
    }

    fun alertdialog() {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage("입력하신 정보는 존재하지 않습니다.")
        builder.setPositiveButton("확인",
            object : DialogInterface.OnClickListener {
                override fun onClick(
                    dialog: DialogInterface, id: Int
                ) {
                    dialog.cancel();
                }
            })
        builder.create()
        builder.show()
    }
}