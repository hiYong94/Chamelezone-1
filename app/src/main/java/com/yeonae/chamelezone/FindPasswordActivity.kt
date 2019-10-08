package com.yeonae.chamelezone

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_find_password.*

class FindPasswordActivity : AppCompatActivity() {
    val testEmail = "heimish_08@naver.com"
    val testPhone = "01049403065"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)

        btn_find_password.setOnClickListener {
            passwordCheck("${edt_email.text}", "${edt_phone.text}")
        }

        btn_back.setOnClickListener {
            finish()
        }
    }

    fun passwordCheck(email: String, phone: String) {
        if (email == testEmail) {
            if (phone == testPhone) {
                Toast.makeText(applicationContext, "임시 비밀번호가 이메일로 발송되었습니다.", Toast.LENGTH_LONG)
                    .show()
            } else {
                alertdialog()
            }
        } else {
            alertdialog()
        }
    }

    fun alertdialog() {
        val builder = AlertDialog.Builder(this)
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