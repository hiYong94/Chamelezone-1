package com.yeonae.chamelezone

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_join.*
import java.util.regex.Pattern


class JoinActivity : AppCompatActivity() {
    val testEmail = "heimish_08@naver.com"
    val testNickname = "yeonvely"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        join_email.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    val p = Pattern.compile("^[a-zA-Z0-9_]+[@]+[a-zA-Z]+[.]+[a-zA-Z]+")
                    val m = p.matcher(join_email.text.toString())

                    if (!m.matches()) {
                        Toast.makeText(
                            applicationContext,
                            "이메일 형식이 올바르지 않습니다.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    else {
                        if (testEmail == "${join_email.text}") {
                            Toast.makeText(
                                applicationContext,
                                "이미 존재하는 이메일 입니다.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "사용 가능한 이메일 입니다.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        })

        join_password.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    val p = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}")
                    val m = p.matcher(join_password.text.toString())

                    if (!m.matches()) {
                        Toast.makeText(
                            applicationContext,
                            "영문, 숫자 포함 8~16자로 입력해주세요.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        })

        join_nickname.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    val p = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{1,20}")
                    val m = p.matcher(join_nickname.text.toString())

                    if (!m.matches()) {
                        Toast.makeText(
                            applicationContext,
                            "한글, 영문, 숫자 포함 1~20자로 입력해주세요.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    else{
                        if (testNickname == "${join_nickname.text}") {
                            Toast.makeText(
                                applicationContext,
                                "이미 존재하는 닉네임 입니다.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "사용 가능한 닉네임 입니다.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        })

        btn_back.setOnClickListener {
            finish()
        }
    }
}