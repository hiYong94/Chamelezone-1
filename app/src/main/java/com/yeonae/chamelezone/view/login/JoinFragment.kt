package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.repository.member.MemberRepositoryImpl
import com.yeonae.chamelezone.data.source.local.member.MemberLocalDataSourceImpl
import com.yeonae.chamelezone.data.source.remote.member.MemberRemoteDataSourceImpl
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.view.login.presenter.JoinContract
import com.yeonae.chamelezone.view.login.presenter.JoinPresenter
import kotlinx.android.synthetic.main.fragment_join.*
import java.util.regex.Pattern

class JoinFragment : Fragment(), JoinContract.View {
    override lateinit var presenter: JoinContract.Presenter

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG)
            .show()
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
            MemberRepositoryImpl.getInstance(
                MemberRemoteDataSourceImpl.getInstance(RetrofitConnection.memberService),
                MemberLocalDataSourceImpl.getInstance()
            ), this
        )
        checkType()

        btn_back.setOnClickListener {
            (activity as LoginActivity).back(this)
        }

        btn_join.setOnClickListener {
            presenter.userRegister(
                "${join_email.text}",
                "${join_password.text}",
                "${join_name.text}",
                "${join_nickname.text}",
                "${join_phone.text}"
            )
        }
    }

    private fun checkType() {
        join_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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
        })

//        join_email.setOnFocusChangeListener { view, hasFocus ->
//            if (hasFocus) {
//                email_layout.isErrorEnabled = false
//            } else {
//                val p = Pattern.compile("^[a-zA-Z0-9_]+[@]+[a-zA-Z]+[.]+[a-zA-Z]+")
//                val m = p.matcher(join_email.text.toString())
//                if (!m.matches() && "${join_email.text}".isNotEmpty()) {
//                    email_layout.error = "이메일 형식이 올바르지 않습니다."
//                } else if ("${join_email.text}".isEmpty()) {
//                    email_layout.error = "이메일을 입력해주세요."
//                } else {
//                    email_layout.isErrorEnabled = false
//                }
//            }
//        }

        join_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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
        })

        join_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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
        })
    }
}