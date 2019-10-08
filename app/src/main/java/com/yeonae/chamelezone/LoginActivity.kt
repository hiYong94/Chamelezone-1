package com.yeonae.chamelezone

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    val testEmail = "heimish_08@naver.com"
    val testPassword = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_find_email.setOnClickListener {
            val intent = Intent(this, FindEmailActivity::class.java)
            startActivity(intent)
        }
        btn_find_password.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }
        btn_join.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener {
            loginCheck("${edt_email.text}", "${edt_password.text}")
        }
    }

    fun loginCheck(email: String, password: String) {
        if(email.isEmpty()){
            Toast.makeText(applicationContext, "아이디를 입력해주세요!", Toast.LENGTH_LONG).show()
        }
        else if(password.isEmpty()){
            Toast.makeText(applicationContext, "비밀번호를 입력해주세요!", Toast.LENGTH_LONG).show()
        }
        else{
            if (email == testEmail) {
                if (password == testPassword) {
                    Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_LONG).show()
                } else {
                    alertdialog()
                }
            } else {
                alertdialog()
            }
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