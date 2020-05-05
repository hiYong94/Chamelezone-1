package com.yeonae.chamelezone.view.mypage

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.presenter.UserModifyContract
import com.yeonae.chamelezone.view.mypage.presenter.UserModifyPresenter
import kotlinx.android.synthetic.main.activity_user_modify.*
import java.util.regex.Pattern

class UserModifyActivity : AppCompatActivity(), UserModifyContract.View {
    private var memberNumber: Int = 0
    private var checkedNickname: Boolean = false
    private var phone = ""
    private var nickname = ""
    private var checkUserModify: Boolean = false
    override lateinit var presenter: UserModifyContract.Presenter
    var lastInputTime = 0L
    private lateinit var originUser: UserEntity

    override fun showUserInfo(user: UserEntity) {
        originUser = user
        nickname = user.nickname.toString()
        phone = user.phone.toString()
        val email = SpannableStringBuilder(user.email)
        val name = SpannableStringBuilder(user.name)
        user_email.text = email
        user_name.text = name
        user_nickname.text = SpannableStringBuilder(nickname)
        user_phone.text = SpannableStringBuilder(phone)
        memberNumber = user.userNumber ?: 0
    }

    override fun showNicknameMessage(nicknameCheck: String) {
        if (nicknameCheck == CHECK_YES) {
            nickname_layout.isErrorEnabled = false
            checkedNickname = true
            if (checkUserModify) {
                updateMember("${user_password.text}", "${user_nickname.text}", "${user_phone.text}")
            }
        } else if (nicknameCheck == CHECK_NO) {
            if ("${user_nickname.text}" == nickname) {
                nickname_layout.isErrorEnabled = false
                checkedNickname = true
                if (checkUserModify) {
                    if ("${user_password.text}".isNullOrEmpty() &&
                        "${user_nickname.text}" == originUser.nickname &&
                        "${user_phone.text}" == originUser.phone
                    ) {
                        finish()
                    } else {
                        updateMember(
                            "${user_password.text}",
                            "${user_nickname.text}",
                            "${user_phone.text}"
                        )
                    }
                }
            } else {
                nickname_layout.error = getString(R.string.registered_nickname)
                checkedNickname = false
            }
        }
    }

    override fun showMessage(response: Boolean) {
        if (response) {
            Toast.makeText(this, R.string.successful_member_info_modification, Toast.LENGTH_LONG)
                .show()
            val editor = getSharedPreferences("setting", 0)?.edit()
            editor?.clear()
            editor?.putString("ID", "${user_email.text}")
            editor?.putString("PW", "${user_password.text}")
            editor?.apply()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(user_nickname.windowToken, 0)
            imm.hideSoftInputFromWindow(user_password.windowToken, 0)
            imm.hideSoftInputFromWindow(user_phone.windowToken, 0)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_modify)
        user_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        user_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        presenter = UserModifyPresenter(
            Injection.memberRepository(), this
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
            emptyCheck(
                "${user_password.text}",
                "${user_nickname.text}",
                "${user_phone.text}"
            )
        }
    }

    private fun emptyCheck(
        password: String,
        nickName: String,
        phone: String
    ) {
        when {
            nickName.isEmpty() -> Toast.makeText(
                this,
                R.string.enter_nickname,
                Toast.LENGTH_SHORT
            ).show()
            phone.isEmpty() -> Toast.makeText(
                this,
                R.string.enter_phone_number,
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                if (checkedNickname) {
                    updateMember(
                        "${user_password.text}",
                        "${user_nickname.text}",
                        "${user_phone.text}"
                    )
                } else {
                    checkUserModify = true
                    presenter.checkNickname("${user_nickname.text}")
                }
            }
        }
    }

    private fun updateMember(password: String?, nickname: String, phone: String) {
        if (!password_layout.isErrorEnabled) {
            presenter.updateMember(
                memberNumber,
                password,
                nickname,
                phone
            )
        }
    }

    private fun checkPassword() {
        val pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}")
        val matcher = pattern.matcher(user_password.text.toString())
        if (!matcher.matches()) {
            password_layout.error = getString(R.string.password_format)
        } else {
            password_layout.isErrorEnabled = false
        }
    }

    private fun checkNickName() {
        val pattern = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{1,10}")
        val matcher = pattern.matcher(user_nickname.text.toString())
        if (!matcher.matches()) {
            nickname_layout.error = getString(R.string.nickname_format)
        } else {
            nickname_layout.isErrorEnabled = false
        }
    }

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