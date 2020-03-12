package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.SingleDialogFragment
import com.yeonae.chamelezone.view.login.presenter.SecurityCodeContract
import com.yeonae.chamelezone.view.login.presenter.SecurityCodePresenter
import kotlinx.android.synthetic.main.fragment_security_code.*

class SecurityCodeFragment : Fragment(), SecurityCodeContract.View {
    override lateinit var presenter: SecurityCodeContract.Presenter
    var memberNumber = 0
    override fun showResultView(matchResult: Boolean) {
        if (matchResult) {
            (activity as? LoginActivity)?.replace(
                ChangePasswordFragment.newInstance(memberNumber),
                true
            )
        }
    }

    override fun showMessage(message: String) {
        val newFragment = SingleDialogFragment.newInstance(
            R.string.information_not_exist
        )
        fragmentManager?.let { newFragment.show(it, "dialog") }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_security_code, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        memberNumber = arguments?.getInt(MEMBER_NUMBER) ?: 0
        val email = arguments?.getString(EMAIL)
        val phone = arguments?.getString(PHONE)
        presenter = SecurityCodePresenter(
            Injection.memberRepository(App.instance.context()), this
        )

        btn_back.setOnClickListener {
            (activity as LoginActivity).back()
        }

        btn_security_code.setOnClickListener {
            if (email != null && phone != null) {
                presenter.checkSecurityCode("${edt_security_code.text}", email, phone)
            }
        }
    }

    companion object {
        private const val MEMBER_NUMBER = "memberNumber"
        private const val EMAIL = "email"
        private const val PHONE = "phoneNumber"
        fun newInstance(memberNumber: Int, email: String, phone: String) =
            SecurityCodeFragment().apply {
                arguments = Bundle().apply {
                    putInt(MEMBER_NUMBER, memberNumber)
                    putString(EMAIL, email)
                    putString(PHONE, phone)
                }
            }
    }
}