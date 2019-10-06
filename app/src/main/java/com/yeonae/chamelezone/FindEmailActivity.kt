package com.yeonae.chamelezone

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_find_email.*

class FindEmailActivity : AppCompatActivity() {
    val testEmail = "heimish_08@naver.com"
    val testName = "yeonjae"
    val testPhone = "01049403065"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_email)
        btn_email.setOnClickListener {
            emailCheck("${edt_name.text}", "${edt_phone.text}")
        }
        btn_back.setOnClickListener {
            finish()
        }
    }

    fun emailCheck(name: String, phone: String) {
        if (name == testName) {
            if (phone == testPhone) {
                val intent = Intent(this, ConfirmEmailActivity::class.java)
                intent.putExtra("email", testEmail)
                startActivity(intent)
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