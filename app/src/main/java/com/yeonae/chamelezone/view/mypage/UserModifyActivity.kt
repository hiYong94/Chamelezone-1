package com.yeonae.chamelezone.view.mypage

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.NicknameResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.presenter.UserModifyContract
import com.yeonae.chamelezone.view.mypage.presenter.UserModifyPresenter
import kotlinx.android.synthetic.main.activity_user_modify.*
import java.util.regex.Pattern

class UserModifyActivity : AppCompatActivity(), UserModifyContract.View {
    var memberNumber: Int = 0
    private var checkedNickname: Boolean = false

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

    override fun showNicknameMessage(response: NicknameResponse) {
        if (response.nicknameCheck == CHECK_YES) {
            Toast.makeText(applicationContext, R.string.available_nickname, Toast.LENGTH_SHORT)
                .show()
            checkedNickname = true

        } else if (response.nicknameCheck == CHECK_NO) {
            Toast.makeText(applicationContext, R.string.registered_nickname, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun showMessage(response: Boolean) {
        if (response) {
            Toast.makeText(this, R.string.successful_member_info_modification, Toast.LENGTH_LONG)
                .show()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(user_nickname.windowToken, 0)
            imm.hideSoftInputFromWindow(user_password.windowToken, 0)
            imm.hideSoftInputFromWindow(user_phone.windowToken, 0)
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

        user_nickname.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
            if (!b) {
                presenter.checkNickname("${user_nickname.text}")
            }
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
            password_layout.error = getString(R.string.password_format)
        } else if ("${user_password.text}".isEmpty()) {
            password_layout.error = getString(R.string.enter_password)
        } else {
            password_layout.isErrorEnabled = false
        }
    }

    private fun checkNickName() {
        val p = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{1,10}")
        val m = p.matcher(user_nickname.text.toString())
        if (!m.matches()) {
            nickname_layout.error = getString(R.string.nickname_format)
        } else if ("${user_nickname.text}".isEmpty()) {
            nickname_layout.error = getString(R.string.enter_nickname)
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

    companion object {
        const val CHECK_YES = "Y"
        const val CHECK_NO = "N"
    }
}