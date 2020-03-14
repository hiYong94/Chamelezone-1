package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.view.login.presenter.ChangePasswordContract
import com.yeonae.chamelezone.view.login.presenter.ChangePasswordPresenter
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_find_password.btn_back
import java.util.regex.Pattern

class ChangePasswordFragment : Fragment(), ChangePasswordContract.View {
    override lateinit var presenter: ChangePasswordContract.Presenter
    var memberNumber = 0
    var lastInputTime = 0L

    override fun showResultView(response: Boolean) {
        if (response) {
            context?.shortToast(R.string.changed_pw)
        }
        (activity as? LoginActivity)?.back()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        memberNumber = arguments?.getInt(MEMBER_NUMBER) ?: 0

        presenter = ChangePasswordPresenter(
            Injection.memberRepository(), this
        )

        edt_pw.addTextChangedListener(object : TextWatcher {
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

        btn_back.setOnClickListener {
            (activity as? LoginActivity)?.back()
        }

        btn_ok.setOnClickListener {
            typeCheck("${edt_pw.text}", "${edt_check_pw.text}")
        }
    }

    private fun typeCheck(
        password: String,
        checkPassword: String
    ) {
        when {
            password.isEmpty() -> context?.shortToast("")
            checkPassword.isEmpty() -> context?.shortToast("")
            "${edt_pw.text}" != "${edt_check_pw.text}" -> {
                layout_check_pw.error = getString(R.string.not_match_pw)
            }
            else -> {
                layout_check_pw.isErrorEnabled = false
                if (!layout_pw.isErrorEnabled &&
                    !layout_check_pw.isErrorEnabled
                ) {
                    presenter.changePassword("${edt_pw.text}", memberNumber)
                }
            }
        }
    }

    private fun checkPassword() {
        val pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}")
        val matcher = pattern.matcher(edt_pw.text.toString())
        if (!matcher.matches()) {
            layout_pw.error = getString(R.string.password_format)
        } else {
            layout_pw.isErrorEnabled = false
        }
    }

    companion object {
        private const val MEMBER_NUMBER = "memberNumber"
        fun newInstance(memberNumber: Int) = ChangePasswordFragment().apply {
            arguments = Bundle().apply {
                putInt(MEMBER_NUMBER, memberNumber)
            }
        }
    }
}