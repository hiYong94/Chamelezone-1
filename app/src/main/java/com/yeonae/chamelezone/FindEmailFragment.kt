package com.yeonae.chamelezone

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_find_email.*

class FindEmailFragment : Fragment() {
    val testEmail = "heimish_08@naver.com"
    val testName = "yeonjae"
    val testPhone = "01049403065"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_find_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_email.setOnClickListener {
            val confirmEmailFragment = ConfirmEmailFragment()
            val bundle = arguments
            bundle!!.putString("email", testEmail)
            confirmEmailFragment.arguments = bundle

            emailCheck("${edt_name.text}", "${edt_phone.text}")
        }
        btn_back.setOnClickListener {
            (activity as LoginActivity).replace(LoginFragment())
        }
    }

    fun emailCheck(name: String, phone: String) {
        if (name == testName) {
            if (phone == testPhone) {
                (activity as LoginActivity).replace(ConfirmEmailFragment().newInstance())

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