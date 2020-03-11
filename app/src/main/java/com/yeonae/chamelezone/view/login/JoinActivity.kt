package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.os.Handler
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.view.login.presenter.JoinContract
import com.yeonae.chamelezone.view.login.presenter.JoinPresenter
import kotlinx.android.synthetic.main.activity_join.*
import java.util.regex.Pattern

class JoinActivity : AppCompatActivity(), JoinContract.View {
    private var checkedEmail: Boolean = false
    private var checkedNickname: Boolean = false

    override fun showNicknameMessage(nicknameCheck: String) {
        if (nicknameCheck == CHECK_YES) {
            nickname_layout.isErrorEnabled = false
            checkedNickname = true
        } else if (nicknameCheck == CHECK_NO) {
            nickname_layout.error = getString(R.string.registered_nickname)
            checkedNickname = false
        }
        if (checkedEmail && checkedNickname) {
            joinCheck(
                "${join_email.text}",
                "${join_password.text}",
                "${join_name.text}",
                "${join_nickname.text}",
                "${join_phone.text}"
            )
        }
    }

    override fun showEmailMessage(emailCheck: String) {
        if (emailCheck == CHECK_YES) {
            email_layout.isErrorEnabled = false
            checkedEmail = true

        } else if (emailCheck == CHECK_NO) {
            email_layout.error = getString(R.string.registered_email)
            checkedNickname = false
        }

        if (checkedEmail && checkedNickname) {
            joinCheck(
                "${join_email.text}",
                "${join_password.text}",
                "${join_name.text}",
                "${join_nickname.text}",
                "${join_phone.text}"
            )
        }
    }

    override lateinit var presenter: JoinContract.Presenter

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        join_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        join_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        presenter = JoinPresenter(
            Injection.memberRepository(App.instance.context()), this
        )

        checkType()

        btn_back.setOnClickListener {
            finish()
        }

        btn_join.setOnClickListener {
            presenter.checkEmail("${join_email.text}")
            presenter.checkNickname("${join_nickname.text}")
        }

        join_email.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
            if (!b) {
                presenter.checkEmail("${join_email.text}")
            }
        }

        join_nickname.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
            if (!b) {
                presenter.checkNickname("${join_nickname.text}")
            }
        }

    }

    private fun joinCheck(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String
    ) {
        when {
            email.isEmpty() -> Toast.makeText(
                this,
                R.string.enter_email,
                Toast.LENGTH_SHORT
            ).show()
            password.isEmpty() -> Toast.makeText(
                this,
                R.string.enter_password,
                Toast.LENGTH_SHORT
            ).show()
            name.isEmpty() -> Toast.makeText(
                this,
                R.string.enter_name,
                Toast.LENGTH_SHORT
            ).show()
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
            !checkedEmail ->
                email_layout.error = getString(R.string.registered_email)
            !checkedNickname ->
                nickname_layout.error = getString(R.string.registered_nickname)
            else -> {
                if (!email_layout.isErrorEnabled &&
                    !password_layout.isErrorEnabled &&
                    !nickname_layout.isErrorEnabled &&
                    checkedEmail &&
                    checkedNickname
                ) {
                    presenter.userRegister(
                        email, password, name, nickName, phone
                    )
                }
            }
        }
    }

    private fun checkEmail() {
        val p = Pattern.compile("^[a-zA-Z0-9_]+[@]+[a-zA-Z]+[.]+[a-zA-Z]+")
        val m = p.matcher(join_email.text.toString())
        if (!m.matches()) {
            email_layout.error = getString(R.string.email_format)
        } else {
            email_layout.isErrorEnabled = false
        }
    }

    private fun checkPassword() {
        val p = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}")
        val m = p.matcher(join_password.text.toString())
        if (!m.matches()) {
            password_layout.error = getString(R.string.password_format)
        } else {
            password_layout.isErrorEnabled = false
        }
    }

    private fun checkNickName() {
        val p = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{1,10}")
        val m = p.matcher(join_nickname.text.toString())
        if (!m.matches()) {
            nickname_layout.error = getString(R.string.nickname_format)
        } else {
            nickname_layout.isErrorEnabled = false
        }
    }

    var lastInputTime = 0L

    private fun checkType() {
        join_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputTime = System.currentTimeMillis()
                lastInputTime = inputTime
                Handler().postDelayed({
                    if (lastInputTime == inputTime) {
                        checkEmail()
                    }
                }, 500)
            }
        })

        join_password.addTextChangedListener(object : TextWatcher {
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

        join_nickname.addTextChangedListener(object : TextWatcher {
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