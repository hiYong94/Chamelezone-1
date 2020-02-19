package com.yeonae.chamelezone.view.mypage

import android.os.Bundle
import android.os.Handler
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.presenter.UserModifyContract
import com.yeonae.chamelezone.view.mypage.presenter.UserModifyPresenter
import kotlinx.android.synthetic.main.activity_user_modify.*
import java.util.regex.Pattern

class UserModifyActivity() : AppCompatActivity(), UserModifyContract.View {
    var memberNumber: Int = 0
    override fun showUserInfo(user: UserEntity) {
        val nickname = SpannableStringBuilder(user.nickname)
        val phone = SpannableStringBuilder(user.phone)
        val email = SpannableStringBuilder(user.email)
        val name = SpannableStringBuilder(user.name)
        user_email.text = email
        user_name.text = name
        user_nickname.text = nickname
        user_phone.text = phone
        memberNumber = user.userNumber ?: 0
    }

    override fun showMessage(response: Boolean) {
        if (response) {
            Toast.makeText(this, "회원 정보 수정 성공", Toast.LENGTH_LONG)
                .show()
            finish()
        }
    }

    override lateinit var presenter: UserModifyContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_modify)
        user_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        user_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        presenter = UserModifyPresenter(
            Injection.memberRepository(applicationContext), this
        )

        presenter.getUser()
        checkType()

        btn_back.setOnClickListener {
            finish()
        }

        btn_user_modify.setOnClickListener {
            if (!password_layout.isErrorEnabled && !nickname_layout.isErrorEnabled) {
                presenter.updateMember(
                    memberNumber,
                    "${user_password.text}",
                    "${user_nickname.text}",
                    "${user_phone.text}"
                )
            }
        }
    }

    private fun checkPassword() {
        val p = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}")
        val m = p.matcher(user_password.text.toString())
        if (!m.matches()) {
            password_layout.error = "영문, 숫자 조합 8~16자로 입력해주세요."
        } else if ("${user_password.text}".isEmpty()) {
            password_layout.error = "비밀번호를 입력해주세요."
        } else {
            password_layout.isErrorEnabled = false
        }
    }

    private fun checkNickName() {
        val p = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{1,10}")
        val m = p.matcher(user_nickname.text.toString())
        if (!m.matches()) {
            nickname_layout.error = "한글, 영문, 숫자 포함 1~10자로 입력해주세요."
        } else if ("${user_nickname.text}".isEmpty()) {
            nickname_layout.error = "닉네임을 입력해주세요."
        } else {
            nickname_layout.isErrorEnabled = false
        }
    }

    var lastInputTime = 0L

    private fun checkType() {
        user_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputTime = System.currentTimeMillis()
                lastInputTime = inputTime
                Handler().postDelayed({
                    if (lastInputTime == inputTime) {
                        checkPassword()
                    }
                }, 500)
            }
        })

        user_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputTime = System.currentTimeMillis()
                lastInputTime = inputTime
                Handler().postDelayed({
                    if (lastInputTime == inputTime) {
                        checkNickName()
                    }
                }, 500)
            }
        })
    }
}