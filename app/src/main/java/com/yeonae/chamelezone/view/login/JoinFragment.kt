package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.os.Handler
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.login.presenter.JoinContract
import com.yeonae.chamelezone.view.login.presenter.JoinPresenter
import kotlinx.android.synthetic.main.fragment_join.*
import java.util.regex.Pattern

class JoinFragment : Fragment(), JoinContract.View {
    var checkedEmail: Boolean = false
    var checkedNickname: Boolean = false
    override fun showNicknameMessage(response: Boolean) {
        if(response){
            Toast.makeText(context, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT)
                .show()
            checkedNickname = true

        }else if(!response){
            Toast.makeText(context, "이미 가입된 닉네임입니다.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun showEmailMessage(response: Boolean) {
        if(response){
            Toast.makeText(context, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT)
                .show()
            checkedEmail = true

        }else if(!response){
            Toast.makeText(context, "이미 가입된 이메일입니다.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override lateinit var presenter: JoinContract.Presenter

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG)
            .show()
        (activity as LoginActivity).back()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_join, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        join_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        join_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        presenter = JoinPresenter(
            Injection.memberRepository(requireContext()), this
        )

        checkType()

        btn_back.setOnClickListener {
            (activity as LoginActivity).back()
        }

        btn_join.setOnClickListener {
            joinCheck(
                "${join_email.text}",
                "${join_password.text}",
                "${join_name.text}",
                "${join_nickname.text}",
                "${join_phone.text}"
            )
        }

        join_email.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                presenter.checkEmail("${join_email.text}")
            }
        }

        join_nickname.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
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
                requireContext(),
                "아이디를 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            password.isEmpty() -> Toast.makeText(
                requireContext(),
                "비밀번호를 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            name.isEmpty() -> Toast.makeText(
                requireContext(),
                "이름을 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            nickName.isEmpty() -> Toast.makeText(
                requireContext(),
                "닉네임을 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            phone.isEmpty() -> Toast.makeText(
                requireContext(),
                "전화번호를 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                if (!email_layout.isErrorEnabled && !password_layout.isErrorEnabled && !nickname_layout.isErrorEnabled && checkedEmail && checkedNickname) {
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
            email_layout.error = "이메일 형식이 올바르지 않습니다."
        } else if ("${join_email.text}".isEmpty()) {
            email_layout.error = "이메일을 입력해주세요."
        } else {
            email_layout.isErrorEnabled = false
        }
    }

    private fun checkPassword() {
        val p = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}")
        val m = p.matcher(join_password.text.toString())
        if (!m.matches()) {
            password_layout.error = "영문, 숫자 조합 8~16자로 입력해주세요."
        } else if ("${join_password.text}".isEmpty()) {
            password_layout.error = "비밀번호를 입력해주세요."
        } else {
            password_layout.isErrorEnabled = false
        }
    }

    private fun checkNickName() {
        val p = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{1,10}")
        val m = p.matcher(join_nickname.text.toString())
        if (!m.matches()) {
            nickname_layout.error = "한글, 영문, 숫자 포함 1~10자로 입력해주세요."
        } else if ("${join_nickname.text}".isEmpty()) {
            nickname_layout.error = "닉네임을 입력해주세요."
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
}