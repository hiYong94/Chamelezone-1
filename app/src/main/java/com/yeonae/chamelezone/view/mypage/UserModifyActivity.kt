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
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.presenter.UserModifyContract
import com.yeonae.chamelezone.view.mypage.presenter.UserModifyPresenter
import kotlinx.android.synthetic.main.activity_user_modify.*
import java.util.regex.Pattern

class UserModifyActivity : AppCompatActivity(), UserModifyContract.View {
    var memberNumber: Int = 0
    private var checkedNickname: Boolean = false
    var phone = ""
    var nickname = ""

    override fun showUserInfo(user: UserEntity) {
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
        } else if (nicknameCheck == CHECK_NO) {
            nickname_layout.error = getString(R.string.registered_nickname)
            checkedNickname = false
        }
        if (checkedNickname) {
            updateCheck()
            Log.d("updateMember", checkedNickname.toString())
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
            Injection.memberRepository(App.instance.context()), this
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
            presenter.checkNickname("${user_nickname.text}")
        }
    }

    private var updateNickname = ""
    private var updatePhone = ""

    private fun updateCheck() {
        if(user_password.text.toString().isEmpty()){
            password_layout.isErrorEnabled = false
        }
        if (!password_layout.isErrorEnabled && !nickname_layout.isErrorEnabled) {
            when {
                user_nickname.text!!.isEmpty() -> updateNickname = nickname
                user_phone.text!!.isEmpty() -> updatePhone = phone
                else -> {
                    updateNickname = "${user_nickname.text}"
                    updatePhone = "${user_phone.text}"
                }
            }
            updateMember("${user_password.text}", updateNickname, updatePhone)
            Log.d("updateMember", "$updateNickname $nickname $phone")
        }
    }

    private fun updateMember(password: String?, nickname: String, phone: String) {
        Log.d("updateMember", "$password $nickname $phone")
        presenter.updateMember(
            memberNumber,
            password,
            nickname,
            phone
        )
    }

    private fun checkPassword() {
        val p = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}")
        val m = p.matcher(user_password.text.toString())
        if (!m.matches()) {
            password_layout.error = getString(R.string.password_format)
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